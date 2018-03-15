package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.CloseIntakeClamp;
import org.usfirst.frc.team2928.Command.DriveShoulder;
import org.usfirst.frc.team2928.Command.OpenIntakeClamp;

public class Unfold extends CommandGroup {
    public Unfold()
    {
        addSequential(new CloseIntakeClamp());
        addSequential(new DriveShoulder(0.6), 3);
        addSequential(new OpenIntakeClamp());
        addSequential(new DriveShoulder(-0.4), 2);
    }

    public Unfold(Command afterUnfold)
    {
        this();
        addSequential(afterUnfold);
    }

    public Unfold(Command afterUnfold, double timeout)
    {
        this();
        addSequential(afterUnfold, timeout);
    }
}
