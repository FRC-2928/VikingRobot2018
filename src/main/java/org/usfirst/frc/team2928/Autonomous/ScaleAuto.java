package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.SequentialCommandGroupBuilder;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class ScaleAuto extends CommandGroup {
    public ScaleAuto(Field.FieldPosition target, Field.FieldPosition start)
    {
        SequentialCommandGroupBuilder driveCommand = new SequentialCommandGroupBuilder();
        SequentialCommandGroupBuilder armCommand = new SequentialCommandGroupBuilder();
        if (target == start)
        {
            switch (target)
            {
                case LEFT:
                {
                    driveCommand.addCommand(new FollowProfile("leftScaleFromLeftSide"), 7.35);
                    break;
                }
                case RIGHT:
                {
                    driveCommand.addCommand(new FollowProfile("rightScaleFromRightSide"), 7.35);
                    break;
                }
                default:
            }
        } else
        {
            driveCommand.addCommand(new FollowProfile("crossLineFromSide"));
        }

        armCommand
                .delay(4)
                .addCommand(new RunShoulder(0.8), 3)
                .delay(0.2)
                .addCommand(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.2)
                .addCommand(new SetGrabber(Grabber.GrabberState.CLOSE))
                .addCommand(new RunShoulder(-0.5), 2);

        addParallel(driveCommand.build());
        addParallel(armCommand.build());
    }
}
