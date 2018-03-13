package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class CloseGrabber extends Command {
    @Override
    public boolean isFinished()
    {
        return true;
    }

    public CloseGrabber()
    {
        requires(Robot.grabber);
    }

    public void initialize()
    {
        Robot.grabber.close();
    }

    public void execute()
    {

    }
}
