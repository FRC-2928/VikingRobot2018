package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Command.Intake.RunAngle;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Command.OneShotCommand;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Robot;
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
                    driveCommand
                            .addSequential(new FollowProfile("leftSideToScale"), 8)
                            .addSequential(new FollowProfile("90Right"), 0.6)
                            .addSequential(new OneShotCommand(() -> {Robot.chassis.drivetrain.setTalons(0.25, -0.25);}, Robot.chassis.drivetrain), 0.25)
                            .addSequential(new OneShotCommand(Robot.chassis.drivetrain::resetTalons, Robot.chassis.drivetrain));
                    break;
                }
                case RIGHT:
                {
                    driveCommand
                            .addSequential(new FollowProfile("rightSideToScale"), 8)
                            .addSequential(new FollowProfile("90Left"), 0.6)
                            .addSequential(new OneShotCommand(() -> {Robot.chassis.drivetrain.setTalons(-0.25, 0.25);}, Robot.chassis.drivetrain), 0.25)
                            .addSequential(new OneShotCommand(Robot.chassis.drivetrain::resetTalons, Robot.chassis.drivetrain));
                    break;
                }
                default:
            }
        } else
        {
            driveCommand.addSequential(new FollowProfile("crossLineFromSide"));
        }
        driveCommand.delay(0.2).addSequential(new FollowProfile("reverseFiveFeet"));
        if(start==target)
            armCommand
                .delay(3)
                .addSequential(new RunShoulder(0.8), 5)
                .delay(0.7)
                .addSequential(new SetGrabber(Grabber.GrabberState.OPEN))
                .addParallel(new RunShoulder(0.6), 0.5)
                .delay(0.2)
                .addSequential(new SetGrabber(Grabber.GrabberState.CLOSE))
                .delay(0.2)
                .addParallel(new SetClamp(Clamp.ClampState.OPEN))
                .addSequential(new RunAngle(-1), 1.5)
                .addParallel(new SetClamp(Clamp.ClampState.CLOSE))
                .addSequential(new RunShoulder(-0.5), 2);
        else
            armCommand.addSequential(new Unfold());

        addParallel(driveCommand.build());
        addParallel(armCommand.build());
    }
}
