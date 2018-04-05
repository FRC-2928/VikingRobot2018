package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Command.Intake.RunAngle;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class ScaleAuto extends CommandGroup {
    public ScaleAuto(Field.FieldPosition target, Field.FieldPosition start)
    {
        CommandGroupBuilder driveCommand = new CommandGroupBuilder();
        CommandGroupBuilder armCommand = new CommandGroupBuilder();
        if (target == start)
        {
            switch (target)
            {
                case LEFT:
                {
                    driveCommand.addSequential(new FollowProfile("leftScaleFromLeftSide"), 7.35);
                    break;
                }
                case RIGHT:
                {
                    driveCommand.addSequential(new FollowProfile("rightScaleFromRightSide"), 7.35);
                    break;
                }
                default:
            }
        } else
        {
            driveCommand.addSequential(new FollowProfile("crossLineFromSide"));
        }
        driveCommand.delay(0.2).addSequential(new FollowProfile("reverseFiveFeet"));
        armCommand
                .delay(3)
                .addSequential(new RunShoulder(0.8), 4)
                .delay(0.2)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.2)
                .addSequential(new SetGrabber(Grabber.GrabberState.CLOSE))
                .delay(0.2)
                .addParallel(new SetClamp(Clamp.ClampState.OPEN))
                .addSequential(new RunAngle(-1), 1.5)
                .addSequential(new RunShoulder(-0.5), 2);

        addParallel(driveCommand.build());
        addParallel(armCommand.build());
    }
}
