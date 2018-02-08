package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Command.JoystickDrive;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Drivebase extends Subsystem {

    public final WPI_TalonSRX left;
    private final WPI_TalonSRX leftSlave;
    public final WPI_TalonSRX right;
    private final WPI_TalonSRX rightSlave;

    private DifferentialDrive drive;

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
    }

    public void arcadeDrive(double xSpeed, double zRotate, boolean xSquared)
    {
        double leftOutput;
        double rightOutput;
        if (xSquared)
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
        drive.arcadeDrive(move, rotate, false);
        SmartDashboard.putNumber("gyro", getAngle());
    }

    public double getAngle()
    {
        throw new NotImplementedException();
    }

    public void setClosedLoop()
    {

    }
}