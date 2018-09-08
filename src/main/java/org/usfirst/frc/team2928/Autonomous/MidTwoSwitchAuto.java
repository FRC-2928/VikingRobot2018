package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Arm.RunSlider;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Command.Intake.RunAngle;
import org.usfirst.frc.team2928.Command.Intake.RunMotors;
import org.usfirst.frc.team2928.Command.OneShotCommand;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class MidTwoSwitchAuto extends CommandGroup {
    public MidTwoSwitchAuto(Field.FieldPosition target) {
        String approachProfile = null;
        String cubeProfile = null;
        String positioningProfile = null;
        String exitProfile = null;
        switch (target) {
            case MIDDLE: {
                System.err.println("MidTwoSwitchAuto called with Field.instance.nearSwitch == FieldPosition.MIDDLE, but there is no middle switch!");
                return;
            }
            case LEFT: {
                approachProfile = "leftSwitchFromCenter";
                cubeProfile = "cubeFromLeft";
                positioningProfile = "backupFromLeft";
                exitProfile = "crossLineFromBehindLeftSwitch";
                break;
            }
            case RIGHT: {
                approachProfile = "rightSwitchFromCenter";
                cubeProfile = "cubeFromRight";
                positioningProfile = "backupFromRight";
                exitProfile = "crossLineFromBehindRightSwitch";
                break;
            }
            default: {
                System.err.println("MidTwoSwitchAuto called with an invalid value of Field.instance.nearSwitch");
            }
        }
        CommandGroupBuilder driveCommandGroup = new CommandGroupBuilder();
        CommandGroupBuilder armCommandGroup = new CommandGroupBuilder();

        driveCommandGroup
                .addSequential(new FollowProfile(approachProfile), 4.3) // Takes 4.15 seconds to drive to the switch
                .addSequential(new FollowProfile("reverseEightFeet"), 4.0) // Takes 2.7 seconds to drive back five feet
                .delay(0.1)
                .addSequential(new FollowProfile(cubeProfile), 2.5) //11 seconds in
                .delay(0.1)
                .addSequential(new FollowProfile(positioningProfile),2.1) //13.2 seconds in
                .delay(0.1)
                .addSequential(new FollowProfile("forwardEightFeet"), 4) //Won't make it, will trim down when after testing
                .addSequential(new OneShotCommand(Robot.chassis.drivetrain::stopProfileDrive, Robot.chassis.drivetrain))
                .delay(0.25);

        armCommandGroup
                .delay(1.3)
                .addSequential(new RunShoulder(0.8), 1.8)
                .delay(1)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.2) //4.3 seconds in
                .addSequential(new RunAngle(-1), 1.5)
                .delay(0.1)
                .addSequential(new SetGrabber(Grabber.GrabberState.CLOSE))
                .addParallel(new RunShoulder(-0.7), 1.5)
                .addParallel(new RunSlider(-0.9),1.5)
                .delay(0.1)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                .delay(0.1)
                .addSequential(new SetClamp(Clamp.ClampState.CLOSE))
                .delay(0.1) //7.7 seconds in
                .addSequential(new RunMotors(.7), 5)
                .delay(0.1) //12.8 seconds in
                .addSequential(new SetGrabber(Grabber.GrabberState.CLOSE))
                .delay(0.1)
                .addSequential(new SetClamp(Clamp.ClampState.OPEN))
                .delay(0.1)
                .addSequential(new RunShoulder(0.8), 1.8)
                .delay(0.6)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN));



        addParallel(driveCommandGroup.build());
        addSequential(armCommandGroup.build());
    }
}
