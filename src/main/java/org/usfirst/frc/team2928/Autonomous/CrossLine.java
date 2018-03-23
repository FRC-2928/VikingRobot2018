package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;

public class CrossLine extends CommandGroup {
    public CrossLine()
    {
        addSequential(new Unfold());
        addSequential(new FollowProfile("crossLineFromSide"));
    }
}
