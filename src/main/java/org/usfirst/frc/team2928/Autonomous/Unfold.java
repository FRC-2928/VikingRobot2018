package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Intake.RunAngle;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class Unfold extends CommandGroup {
    public Unfold() {
        addSequential(new SetClamp(Clamp.ClampState.CLOSE));
        addSequential(new RunShoulder(0.6), 1.5);
        addSequential(new SetClamp(Clamp.ClampState.OPEN));
        addParallel(new RunAngle(-1), 1.5);
        addSequential(new RunShoulder(-0.3), 1);
        addSequential(new SetClamp(Clamp.ClampState.CLOSE));
    }
}
