package org.usfirst.frc.team2928.Command.Arm;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleGrabber extends Command {

    public ToggleGrabber()
    {
        requires(Robot.arm.grabber);
    }

    public void initialize()
    {
        Robot.arm.grabber.toggle();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
