package org.usfirst.frc.team2928.Subsystem.Chassis;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Autonomous.Profile;
import org.usfirst.frc.team2928.Command.Chassis.JoystickDrive;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

public class Drivetrain extends Subsystem {

    public final WPI_TalonSRX left;
    private final WPI_TalonSRX leftSlave;
    public final WPI_TalonSRX right;
    private final WPI_TalonSRX rightSlave;

    private MotionProfileStatus statusLeft;
    private MotionProfileStatus statusRight;

    private PigeonIMU pigeon;
    private DifferentialDrive drive;

    private Profile profile;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public Drivetrain()
    {
        // Initialize talons
        left = new WPI_TalonSRX(RobotMap.TALON_FRONT_LEFT);
        leftSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_LEFT);
        leftSlave.set(ControlMode.Follower, RobotMap.TALON_FRONT_LEFT);

        right = new WPI_TalonSRX(RobotMap.TALON_FRONT_RIGHT);
        rightSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_RIGHT);
        rightSlave.set(ControlMode.Follower, RobotMap.TALON_FRONT_RIGHT);

        // Invert the right side of the drivetrain, so both sides go the same way
        right.setInverted(true);
        rightSlave.setInverted(true);

        for (WPI_TalonSRX t : new WPI_TalonSRX[]{left, right}) {
            // PIDF constants for motion profiling
            t.config_kP(0, RobotConstants.TALON_P, RobotConstants.CAN_TIMEOUT_MS);
            t.config_kI(0, RobotConstants.TALON_I, RobotConstants.CAN_TIMEOUT_MS);
            t.config_kD(0, RobotConstants.TALON_D, RobotConstants.CAN_TIMEOUT_MS);
            t.config_kF(0, RobotConstants.TALON_F, RobotConstants.CAN_TIMEOUT_MS);

            // We use quad encoders on this years robot
            t.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotConstants.CAN_TIMEOUT_MS);
            // Invert the encoder readings so a forward move on the robot is a positive change in the encoder reading
            t.setSensorPhase(true);
            // No clue what this does, but the manual says to enable it anyway.
            t.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, (int)RobotConstants.PROFILE_TICK_MS, RobotConstants.CAN_TIMEOUT_MS);
            // Default is 4% deadband, we want less.
            t.configNeutralDeadband(0.01, RobotConstants.CAN_TIMEOUT_MS);

            t.configOpenloopRamp(1.5, RobotConstants.CAN_TIMEOUT_MS);

        }

        drive = new DifferentialDrive(left, right);

        pigeon = new PigeonIMU(RobotMap.PIGEON);

        statusLeft = new MotionProfileStatus();
        statusRight = new MotionProfileStatus();

        setBrakeMode(false);
    }

    public void drive(double move, double rotate) {
        this.drive(move, rotate, true);
    }

    public void drive(double move, double rotate, boolean squaredInputs)
    {
        drive.arcadeDrive(rotate, move, squaredInputs); // WPILIB is still backwards
        SmartDashboard.putNumber("gyro", getYaw());
        System.out.println(left.getSelectedSensorVelocity(0));
    }
    public double getYaw() {
        double[] angles = {0, 0, 0};
        pigeon.getYawPitchRoll(angles);
        return angles[0];
    }

    public void zeroSensors() {
        left.setSelectedSensorPosition(0, 0, RobotConstants.CAN_TIMEOUT_MS);
        right.setSelectedSensorPosition(0, 0, RobotConstants.CAN_TIMEOUT_MS);
        pigeon.setYaw(0, RobotConstants.CAN_TIMEOUT_MS);
    }

    public int[] getEncoders() {
        return new int[]{left.getSelectedSensorPosition(0), right.getSelectedSensorPosition(0)};
    }

    public void setBrakeMode(boolean brake) {
        left.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
        for (int i = 0; i < 32; i++)
            profile.sendNextPoint(left, right);
    }

    public void profileDrive()
    {
        left.getMotionProfileStatus(statusLeft);
        right.getMotionProfileStatus(statusRight);

        right.set(ControlMode.MotionProfile, statusRight.isLast ? 2 : 1); // Hold if at last point
        left.set(ControlMode.MotionProfile, statusLeft.isLast ? 2 : 1);

        left.processMotionProfileBuffer();
        right.processMotionProfileBuffer();

        if (statusLeft.btmBufferCnt < 32 && !doneWithProfile())
        {
            for (int i = 0; i < 32; i++)
                profile.sendNextPoint(left, right);
        }
    }

    public boolean doneWithProfile()
    {
        return statusLeft.isLast && statusRight.isLast;
    }

    public void resetTalons()
    {
        left.clearMotionProfileTrajectories();
        left.set(ControlMode.PercentOutput, 0);
        right.clearMotionProfileTrajectories();
        right.set(ControlMode.PercentOutput, 0);
        zeroSensors();
    }
}