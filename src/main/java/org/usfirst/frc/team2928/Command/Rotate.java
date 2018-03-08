package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;

public class Rotate extends PIDCommand {

    public Rotate(double angle)
    {
        super(1, 0,0);
        requires(Robot.drivebase);
        setSetpoint(angle);
    }

    public void initialize()
    {
        Robot.drivebase.zeroSensors();
    }

    @Override
    protected double returnPIDInput() {
        return Robot.drivebase.getAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.drivebase.drive(0, output);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}