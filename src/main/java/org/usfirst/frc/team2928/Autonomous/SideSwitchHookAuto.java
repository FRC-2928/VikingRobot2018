package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.OneShotCommand;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class SideSwitchHookAuto extends CommandGroup {
    public SideSwitchHookAuto(Field.FieldPosition target, Field.FieldPosition startingPosition)
    {
        if (target == startingPosition)
        {
            CommandGroupBuilder driveCommand = new CommandGroupBuilder();
            CommandGroupBuilder armCommand = new CommandGroupBuilder();
            String approachProfile;
            if (target == Field.FieldPosition.LEFT)
                approachProfile = "leftSideSwitchFromLeftSide";
            else
                approachProfile = "rightSideSwitchFromRightSide";
            driveCommand.addSequential(new FollowProfile(approachProfile), 5.1)
                        .delay(0.25)
                        .addSequential(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive))
                        .addSequential(new FollowProfile("reverseFiveFeet"), 2.3) // cut it off early
                        .addSequential(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive));
            armCommand
                    .delay(2.1)
                    .addSequential(new RunShoulder(0.8), 1.9)
                    .delay(0.7)
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
