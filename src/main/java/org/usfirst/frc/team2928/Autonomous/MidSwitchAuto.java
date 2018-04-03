package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.OneShotCommand;
import org.usfirst.frc.team2928.Command.SequentialCommandGroupBuilder;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class MidSwitchAuto extends CommandGroup {
    public MidSwitchAuto(Field.FieldPosition target, boolean crossLine) {
        String approachProfile = null;
        String exitProfile = null;
        switch (target) {
            case MIDDLE: {
                System.err.println("MidSwitchAuto called with Field.instance.nearSwitch == FieldPosition.MIDDLE, but there is no middle switch!");
                return;
            }
            case LEFT: {
                approachProfile = "leftSwitchFromCenter";
                exitProfile = "crossLineFromBehindLeftSwitch";
                break;
            }
            case RIGHT: {
                approachProfile = "rightSwitchFromCenter";
                exitProfile = "crossLineFromBehindRightSwitch";
                break;
            }
            default: {
                System.err.println("MidSwitchAuto called with an invalid value of Field.instance.nearSwitch");
            }
        }
        SequentialCommandGroupBuilder driveCommandGroup = new SequentialCommandGroupBuilder();
        SequentialCommandGroupBuilder armCommandGroup = new SequentialCommandGroupBuilder();

        driveCommandGroup
                .addCommand(new FollowProfile(approachProfile), 4.3) // Takes 4.15 seconds to drive to the switch
                .addCommand(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain)) // This should be called by FollowProfile.end(), but we should be sure, and we don't have time to test
                .delay(0.25)
                .addCommand(new FollowProfile("reverseFiveFeet"), 2.8) // Takes 2.7 seconds to drive back five feet
                .addCommand(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain))
                .delay(0.25);
        if (crossLine) {
            driveCommandGroup
                    .addCommand(new FollowProfile(exitProfile), 4.6) // Takes 4.45 seconds to drive past the line
                    .addCommand(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain));
        }

        armCommandGroup
                .delay(1.3)
                .addCommand(new RunShoulder(0.8), 1.8)
                .delay(0.6)
                .addCommand(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.1)
                .addCommand(new RunShoulder(.8), 0.3);


        addParallel(driveCommandGroup.build());
        addSequential(armCommandGroup.build());
        addSequential(new Unfold());
    }
}
