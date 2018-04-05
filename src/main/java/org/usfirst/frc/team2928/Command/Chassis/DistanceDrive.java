package org.usfirst.frc.team2928.Command.Chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;

public class DistanceDrive extends Command {
    private int setpoint;
    private boolean motorSafetyBackup = true;
    public DistanceDrive(double feet) {
        requires(Robot.chassis.drivetrain);
        setpoint = (int)(RobotConstants.DRIVE_TICKS_PER_FOOT * feet);
    }

    @Override
    protected void initialize() {
        motorSafetyBackup = Robot.chassis.drivetrain.getMotorSafetyEnabled();
        Robot.chassis.drivetrain.setMotorSafetyEnabled(false);
        Robot.chassis.drivetrain.zeroEncoders();
        Robot.chassis.drivetrain.setTalons(ControlMode.MotionMagic, setpoint);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        double[] encoderVelocities = Robot.chassis.drivetrain.getEncoderVelocities();
        double totalVelocityMagnitude = Math.abs(encoderVelocities[0]) + Math.abs(encoderVelocities[1]);
        // Stop if our motors are turning less than 0.1 feet per second
        return totalVelocityMagnitude < (RobotConstants.DRIVE_TICKS_PER_FOOT * 0.1 / 10);
    }

    @Override
    protected void end() {
        Robot.chassis.drivetrain.resetTalons();
        Robot.chassis.drivetrain.setMotorSafetyEnabled(motorSafetyBackup);
    }

    @Override
    protected void interrupted() {
        end();
    }
}