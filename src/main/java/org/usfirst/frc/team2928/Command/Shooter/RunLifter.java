package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunLifter extends Command {

    private double output;
    public boolean isFinished()
    {
        return false;
    }

    public RunLifter(double output)
    {
        this.output = output;
        requires(Robot.shooter.lifter);
    }

    public void execute()
    {
        Robot.shooter.lifter.set(output);
    }

    public void end()
    {
        Robot.shooter.lifter.set(0);
    }

    public void interrupted()
    {
        end();
    }
}
