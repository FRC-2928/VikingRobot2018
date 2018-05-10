package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunIntake extends Command {
    private double output;
    public RunIntake(double output)
    {
        requires(Robot.shooter.intake);
        this.output = output;
    }

    public void initialize()
    {

    }

    public void execute()
    {
        Robot.shooter.intake.set(output);
    }

    public void end()
    {
        Robot.shooter.intake.set(0);
    }

    public void interupted()
    {
        end();
    }

    public boolean isFinished()
    {
        return false;
    }
}
