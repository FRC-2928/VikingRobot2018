package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team2928.Robot;

public class Rotate extends PIDCommand {

    public Rotate(double angle) {
        super(1, 0, 0);
        requires(Robot.drivebase);
        setSetpoint(angle);
    }

    public void initialize() {
        Robot.drivebase.zeroSensors();
    }

    @Override
    protected double returnPIDInput() {
        return Robot.drivebase.getYaw();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.drivebase.drive(0, output);
        System.out.println("Rotating");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}