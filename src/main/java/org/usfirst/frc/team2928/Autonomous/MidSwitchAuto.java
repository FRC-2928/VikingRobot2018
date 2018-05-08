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

public class MidSwitchAuto extends CommandGroup {
    public MidSwitchAuto(Field.FieldPosition target) {
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
        CommandGroupBuilder driveCommandGroup = new CommandGroupBuilder();
        CommandGroupBuilder armCommandGroup = new CommandGroupBuilder();

        driveCommandGroup
                .addSequential(new FollowProfile(approachProfile), 4.3) // Takes 4.15 seconds to drive to the switch
                .addSequential(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain)) // This should be called by FollowProfile.end(), but we should be sure, and we don't have time to test
                .delay(0.25)
                .addSequential(new FollowProfile("reverseFiveFeet"), 2.8) // Takes 2.7 seconds to drive back five feet
                .addSequential(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain))
                .delay(0.25);

        armCommandGroup
                .delay(1.3)
                .addSequential(new RunShoulder(0.8), 1.8)
                .delay(0.6)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.2)
                .addSequential(new RunShoulder(.8), 0.2)
                .addSequential(new SetGrabber(Grabber.GrabberState.CLOSE));


        addParallel(driveCommandGroup.build());
        addSequential(armCommandGroup.build());
        addSequential(new Unfold());
    }
}
