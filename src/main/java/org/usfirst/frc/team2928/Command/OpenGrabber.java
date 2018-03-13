package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class OpenGrabber extends Command {
    @Override
    public boolean isFinished()
    {
        return true;
    }

    public OpenGrabber()
    {
        requires(Robot.grabber);
    }

    public void initialize()
    { Robot.grabber.open();
    }

    public void execute()
    {

    }
}
