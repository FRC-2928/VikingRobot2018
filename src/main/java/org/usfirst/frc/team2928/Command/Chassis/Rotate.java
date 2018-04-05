package org.usfirst.frc.team2928.Command.Chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;

// Uses CTRE Motion Magic to rotate the robot a specified number of degrees. CCW-Positive.
public class Rotate extends Command {

    private int setpoint; // For the right talon, left talon is -setpoint.
    private boolean motorSafetyBackup = true;

    public Rotate(double degrees) {
        requires(Robot.chassis.drivetrain);
        this.setpoint = (int)(degrees * Math.PI / 180 * (RobotConstants.AXLE_LENGTH_FEET/2));
    }

    @Override
    protected boolean isFinished() {
        double[] encoderVelocities = Robot.chassis.drivetrain.getEncoderVelocities();
        double totalVelocityMagnitude = Math.abs(encoderVelocities[0]) + Math.abs(encoderVelocities[1]);
        // Stop if our motors are turning less than 0.1 feet per second
        return totalVelocityMagnitude < (RobotConstants.DRIVE_TICKS_PER_FOOT * 0.1 / 10);
    }

    @Override
    public void initialize(){
        motorSafetyBackup = Robot.chassis.drivetrain.getMotorSafetyEnabled();
        // Safety has to be disabled whenever we use a mode that isn't
        Robot.chassis.drivetrain.setMotorSafetyEnabled(false);
        Robot.chassis.drivetrain.zeroEncoders();

        // We can do this because we disable the motor safety.
        Robot.chassis.drivetrain.setTalons(ControlMode.MotionMagic, -setpoint, setpoint);
    }

    @Override
    public void execute(){
    }

    @Override
    public void interrupted(){
        end();
    }

    @Override
    public void end(){
        Robot.chassis.drivetrain.resetTalons();
        Robot.chassis.drivetrain.setMotorSafetyEnabled(motorSafetyBackup);
    }
}