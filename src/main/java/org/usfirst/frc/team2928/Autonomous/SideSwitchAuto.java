package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class SideSwitchAuto extends CommandGroup {
    public SideSwitchAuto(Field.FieldPosition target, Field.FieldPosition startingPosition)
    {
        String approachProfile = null;
        if (target == startingPosition)
        {
            if (target == Field.FieldPosition.LEFT) {
                approachProfile = "leftSwitchFromLeftSide";
            } else
            {
                approachProfile = "rightSwitchFromRightSide";
            }
            CommandGroupBuilder driveCommand = new CommandGroupBuilder();
            CommandGroupBuilder armCommand = new CommandGroupBuilder();
            driveCommand.addSequential(new FollowProfile(approachProfile), 4.1);
            armCommand
                    .delay(1.3)
                    .addSequential(new RunShoulder(0.8), 1.8)
                    .delay(0.6)
                    .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                    .delay(0.1)
                    .addSequential(new RunShoulder(.8), 0.3);
            addParallel(driveCommand.build());
            addSequential(armCommand.build());
        } else
        {
            addSequential(new FollowProfile("crossLineFromSide"));
        }
        addSequential(new Unfold());
    }
}
