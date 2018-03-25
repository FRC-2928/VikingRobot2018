package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.SequentialCommandGroupBuilder;
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
            SequentialCommandGroupBuilder driveCommand = new SequentialCommandGroupBuilder();
            SequentialCommandGroupBuilder armCommand = new SequentialCommandGroupBuilder();
            driveCommand.addCommand(new FollowProfile(approachProfile), 4.1);
            armCommand
                    .wait(1.3)
                    .addCommand(new RunShoulder(0.8), 1.8)
                    .wait(0.6)
                    .addCommand(new SetGrabber(Grabber.GrabberState.OPEN))
                    .wait(0.1)
                    .addCommand(new RunShoulder(.8), 0.3);
            addParallel(driveCommand.build());
            addSequential(armCommand.build());
        } else
        {
            addSequential(new FollowProfile("crossLineFromSide"));
        }
        addSequential(new Unfold());
    }
}
