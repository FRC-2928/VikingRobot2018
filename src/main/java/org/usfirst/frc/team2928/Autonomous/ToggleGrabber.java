package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleGrabber extends Command {

    public ToggleGrabber()
    {
    }

    public void initialize()
    {
        requires(Robot.grabber);
    }

    public void execute()
    {
        Robot.grabber.toggle();
    }
    @Override
    public boolean isFinished()
    {
        return true;
    }
}
