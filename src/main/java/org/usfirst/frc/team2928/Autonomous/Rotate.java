package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team2928.Robot;

public class Rotate extends PIDCommand {

    public Rotate(double angle) {
        // TODO: tune this
        super(0.2, 0, 0);
        requires(Robot.chassis.drivetrain);
        setSetpoint(angle);
    }

    public void initialize() {
        Robot.chassis.drivetrain.zeroSensors();
    }

    @Override
    protected double returnPIDInput() {
        return Robot.chassis.drivetrain.getYaw();
    }

    @Override
    protected void usePIDOutput(double output) {
        // TODO: should output be negative?
        Robot.chassis.drivetrain.drive(0, -output);
        System.out.println("Rotating");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}