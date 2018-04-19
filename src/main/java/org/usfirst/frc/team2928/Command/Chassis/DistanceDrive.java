package org.usfirst.frc.team2928.Command.Chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;

public class DistanceDrive extends Command {
    private int setpoint;
    private double previousVelocity;
    private int decelCounter;
    private boolean hasStartedDecel;
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
        previousVelocity = -1;
        decelCounter = 0;
        hasStartedDecel = false;
        Robot.chassis.drivetrain.setTalons(ControlMode.MotionMagic, setpoint);
    }

    @Override
    protected void execute() {
        double velocity = Robot.chassis.drivetrain.getAverageVelocityMagnitude();
        if (velocity < previousVelocity)
        {
            decelCounter++;
        } else
        {
            decelCounter = 0;
        }
        if (decelCounter > 3)
        {
            hasStartedDecel = true;
        }
        previousVelocity = velocity;
    }

    @Override
    protected boolean isFinished() {
        return hasStartedDecel && Robot.chassis.drivetrain.getAverageVelocityMagnitude() < RobotConstants.TALON_CRUISE_VELOCITY * 0.02;
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