package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.ToggleGrabber;
import org.usfirst.frc.team2928.Field;

public class SmartAuto extends CommandGroup {
    public SmartAuto(Field.Position start, AutoTarget target)
    {
        addSequential(new DriveToTarget(start, target));
        addSequential(new ToggleGrabber());
    }
}
