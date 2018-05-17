package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Chassis.DistanceDrive;
import org.usfirst.frc.team2928.Command.Chassis.Rotate;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Command.Intake.RunAngle;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class MagicScaleAuto extends CommandGroup {
    public MagicScaleAuto(Field.FieldPosition target, Field.FieldPosition start)
    {
        if (target == start)
        {
            addParallel(new RunShoulder(0.8), 4);
            addSequential(new WaitCommand(0.8));
            addParallel(new SetClamp(Clamp.ClampState.OPEN));
            addParallel(new RunAngle(-1), 2);
            addSequential(new DistanceDrive(27 - 1.54));
            addParallel(new SetClamp(Clamp.ClampState.CLOSE));
            addSequential(new WaitCommand(0.2));
            addSequential(new Rotate(target == Field.FieldPosition.LEFT ? -100 : 100));
            addSequential(new WaitCommand(0.2));
            addSequential(new SetGrabber(Grabber.GrabberState.OPEN));
            addSequential(new WaitCommand(0.2));
            addSequential(new DistanceDrive(-3));
            addSequential(new RunShoulder(-0.6), 2);
        } else if (start != Field.FieldPosition.MIDDLE)
        {
            addSequential(new CrossLine());
        }
    }
}
