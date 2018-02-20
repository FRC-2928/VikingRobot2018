package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunIntake extends Command {
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public RunIntake()
    {
        requires(Robot.intake);
    }

    public void initialize()
    {
        Robot.intake.setIntake(0.5);
    }

    public void execute()
    {
        System.out.println("Spinning");
    }

    public void end()
    {
        Robot.intake.setIntake(0.0);
    }
}
