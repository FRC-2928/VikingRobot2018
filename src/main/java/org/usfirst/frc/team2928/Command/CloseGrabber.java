package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class CloseGrabber extends Command {
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public CloseGrabber()
    {
        requires(Robot.intake);
    }

    public void initialize()
    {
        Robot.intake.close();
    }

    public void execute()
    {
        System.out.println("Opening Intake");
    }
}
