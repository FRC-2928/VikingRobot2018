package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team2928.Command.JoystickDrive;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

public class Drivebase extends Subsystem {

    public final WPI_TalonSRX left;
    private final WPI_TalonSRX leftSlave;
    public final WPI_TalonSRX right;
    private final WPI_TalonSRX rightSlave;

    private PigeonIMU pigeon;
    private DifferentialDrive drive;

    private boolean closedLoop;


    public Trajectory.Config config;
    private EncoderFollower leftFollower;
    private EncoderFollower rightFollower;

    private TankModifier trajectory;
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    // TODO: constants for talon ports
    // TODO: Do we need to keep left/rightSlave around?
    public Drivebase() {
        left = new WPI_TalonSRX(RobotMap.TALON_FRONT_LEFT);
        right = new WPI_TalonSRX(RobotMap.TALON_FRONT_RIGHT);

        leftSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_RIGHT);
        leftSlave.set(ControlMode.Follower, left.getBaseID());
        rightSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_RIGHT);
        rightSlave.set(ControlMode.Follower, right.getBaseID());

        int maxTicksPer100ms = (int)(Conversions.FeetToTicks(RobotConstants.MAX_FEET_PER_SECOND)/10);

        for (WPI_TalonSRX t : new WPI_TalonSRX[] {left, right})
        {
            t.config_kP(RobotConstants.DRIVE_PID_POSITION_SLOT, RobotConstants.DRIVE_POSITION_P, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kI(RobotConstants.DRIVE_PID_POSITION_SLOT, RobotConstants.DRIVE_POSITION_I, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kD(RobotConstants.DRIVE_PID_POSITION_SLOT, RobotConstants.DRIVE_POSITION_D, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kF(RobotConstants.DRIVE_PID_POSITION_SLOT, RobotConstants.DRIVE_POSITION_F, RobotConstants.TALON_TIMEOUT_MS);

            t.config_kP(RobotConstants.DRIVE_PID_VELOCITY_SLOT, RobotConstants.DRIVE_VELOCITY_P, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kI(RobotConstants.DRIVE_PID_VELOCITY_SLOT, RobotConstants.DRIVE_VELOCITY_I, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kD(RobotConstants.DRIVE_PID_VELOCITY_SLOT, RobotConstants.DRIVE_VELOCITY_D, RobotConstants.TALON_TIMEOUT_MS);
            t.config_kF(RobotConstants.DRIVE_PID_VELOCITY_SLOT, RobotConstants.DRIVE_VELOCITY_F, RobotConstants.TALON_TIMEOUT_MS);

            t.configMotionCruiseVelocity((int)(maxTicksPer100ms*0.75), RobotConstants.TALON_TIMEOUT_MS);
            t.configMotionAcceleration((int)(maxTicksPer100ms*0.30), RobotConstants.TALON_TIMEOUT_MS);
            t.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);
        }

        drive = new DifferentialDrive(left, right);

        pigeon = new PigeonIMU(RobotMap.PIGEON);

        closedLoop = false;
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.06, 6, 2.0, 60);

        setClosedLoop(true);
    }

    public void arcadeDrive(double xSpeed, double zRotate, boolean squaredInputs)
    {
        double leftOutput;
        double rightOutput;
        if (squaredInputs)
        {
            xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
            zRotate = Math.copySign(zRotate * zRotate, zRotate);
        }

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotate)), xSpeed);
        if (xSpeed >= 0.0)
        {
            if(zRotate >= 0.0)
            {
                leftOutput = maxInput;
                rightOutput = xSpeed - zRotate;
            } else
            {
                leftOutput = xSpeed + zRotate;
                rightOutput = maxInput;
            }
        } else
        {
            if(zRotate >= 0.0)
            {
                leftOutput = xSpeed + zRotate;
                rightOutput = maxInput;
            } else
            {
                leftOutput = maxInput;
                rightOutput = xSpeed - zRotate;
            }
        }
        leftOutput = limit(leftOutput, -1, 1);
        rightOutput = limit(-rightOutput, -1, 1);

        left.set(ControlMode.Velocity, leftOutput * Conversions.FeetToTicks(RobotConstants.MAX_FEET_PER_SECOND));
        right.set(ControlMode.Velocity, rightOutput * Conversions.FeetToTicks(RobotConstants.MAX_FEET_PER_SECOND));
    }

    protected double limit(double value, double min, double max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }

    public void drive(double move, double rotate) {
        if (closedLoop)
            this.arcadeDrive(move, rotate, true);
        else
            drive.arcadeDrive(move, rotate, true);
        SmartDashboard.putNumber("gyro", getAngle());
    }

    public double getAngle()
    {
        double[] angles = {0, 0, 0};
        pigeon.getYawPitchRoll(angles);
        return angles[0];
    }

    public void setClosedLoop(boolean closedLoop)
    {
        this.closedLoop = closedLoop;
    }

    public void setWaypoints(Waypoint[] points)
    {
        Trajectory traj = Pathfinder.generate(points, config);
        this.trajectory = new TankModifier(traj).modify(RobotConstants.AXLE_LENGTH_METERS);
        initSensors();
    }

    public void initSensors()
    {
        leftFollower = new EncoderFollower(trajectory.getLeftTrajectory());
        rightFollower = new EncoderFollower(trajectory.getRightTrajectory());
        leftFollower.configurePIDVA(RobotConstants.PATHFINDER_P, RobotConstants.PATHFINDER_I, RobotConstants.PATHFINDER_D, RobotConstants.PATHFINDER_VELOCTIY_RATIO, RobotConstants.PATHFINDER_ACCEL);
        left.setSelectedSensorPosition(0, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);
        right.setSelectedSensorPosition(0, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);
        leftFollower.configureEncoder(left.getSelectedSensorPosition(0), RobotConstants.DRIVE_TICKS_PER_ROTATION, Conversions.FeetToMeters(RobotConstants.WHEEL_CIRCUMFERENCE_FEET/Math.PI));
        rightFollower.configureEncoder(left.getSelectedSensorPosition(0), RobotConstants.DRIVE_TICKS_PER_ROTATION, Conversions.FeetToMeters(RobotConstants.WHEEL_CIRCUMFERENCE_FEET/Math.PI));
        pigeon.setYaw(0, 10);
    }

    public int[] getEncoders()
    {
        return new int[] {left.getSelectedSensorPosition(0), right.getSelectedSensorPosition(0)};
    }

    public void trajectoryDrive()
    {
        int[] encoderValues = getEncoders();
        double l = leftFollower.calculate(encoderValues[0]);
        double r = rightFollower.calculate(encoderValues[1]);
        double desiredHeading = Pathfinder.r2d(leftFollower.getHeading());
        double headingError = Pathfinder.boundHalfDegrees(desiredHeading - getAngle());
        double turn = 0.8 * (-1.0/80.0) * headingError;
        left.set(l + turn);
        right.set(r - turn);
    }

    public boolean doneWithTrajectory()
    {
        return leftFollower.isFinished() && rightFollower.isFinished();
    }
}