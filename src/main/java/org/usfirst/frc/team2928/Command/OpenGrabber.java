package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class OpenGrabber extends Command {
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public OpenGrabber()
    {
        requires(Robot.intake);
    }

    public void initialize()
    { Robot.intakeClamp.open();
    }

    public void execute()
    {
        System.out.println("Opening Intake");
    }
}
