package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class Unfold extends CommandGroup {
    public Unfold()
    {
        addSequential(new SetClamp(Clamp.ClampState.CLOSE));
        addSequential(new RunShoulder(0.6), 3);
        addSequential(new SetClamp(Clamp.ClampState.OPEN));
        addSequential(new RunShoulder(-0.4), 2);
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
