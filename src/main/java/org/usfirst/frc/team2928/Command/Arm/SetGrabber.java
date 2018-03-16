package org.usfirst.frc.team2928.Command.Arm;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class SetGrabber extends Command {

    private Grabber.GrabberState target;

    public SetGrabber(Grabber.GrabberState target)
    {
        requires(Robot.arm.grabber);
        this.target = target;
    }

    public void initialize()
    {
        Robot.arm.grabber.set(target);
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
