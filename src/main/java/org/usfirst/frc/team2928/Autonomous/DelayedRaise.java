package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class DelayedRaise extends CommandGroup {
    public DelayedRaise(double delay)
    {
        addSequential(new WaitCommand(delay));
        addSequential(new RunShoulder(0.8), 1.5);
        addSequential(new WaitCommand(0.6));
        addSequential(new SetGrabber(Grabber.GrabberState.OPEN));
        addSequential(new RunShoulder(0.6), 2);
    }
}
