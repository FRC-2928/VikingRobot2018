package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleGrabber extends Command {

    public ToggleGrabber()
    {
        requires(Robot.grabber);
    }

    public void initialize()
    {
        Robot.grabber.toggle();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
