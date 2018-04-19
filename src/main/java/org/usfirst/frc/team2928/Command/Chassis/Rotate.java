package org.usfirst.frc.team2928.Command.Chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;

// Uses CTRE Motion Magic to rotate the robot a specified number of degrees. CCW-Positive.
public class Rotate extends Command {

    private int setpoint; // For the right talon, left talon is -setpoint.
    private boolean motorSafetyBackup = true;
    private double previousVelocity;
    private int decelCounter;
    private boolean hasStartedDecel;
    public Rotate(double degrees) {
        requires(Robot.chassis.drivetrain);
        this.setpoint = (int)(RobotConstants.DRIVE_TICKS_PER_FOOT * (degrees / 360 * Math.PI * RobotConstants.AXLE_LENGTH_FEET));
    }

    @Override
    protected boolean isFinished() {
        return hasStartedDecel && Robot.chassis.drivetrain.getAverageVelocityMagnitude() < RobotConstants.TALON_CRUISE_VELOCITY * 0.02;
    }

    @Override
    public void initialize(){
        motorSafetyBackup = Robot.chassis.drivetrain.getMotorSafetyEnabled();
        // Safety has to be disabled whenever we use a mode that isn't
        Robot.chassis.drivetrain.setMotorSafetyEnabled(false);
        Robot.chassis.drivetrain.zeroEncoders();
        previousVelocity = -1;
        decelCounter = 0;
        hasStartedDecel = false;
        // We can do this because we disabled the motor safety.
        Robot.chassis.drivetrain.setTalons(ControlMode.MotionMagic, -setpoint, setpoint);
    }

    @Override
    public void execute(){
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
    public void interrupted(){
        end();
    }

    @Override
    public void end(){
        Robot.chassis.drivetrain.resetTalons();
        Robot.chassis.drivetrain.setMotorSafetyEnabled(motorSafetyBackup);
    }
}