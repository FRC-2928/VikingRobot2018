package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Chassis.ConstantDrive;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;

public class SwitchAuto extends CommandGroup {
    public SwitchAuto(FieldPosition start, FieldPosition target)
    {
        String approachProfile;
        String exitProfile;
        boolean dropBox = true;
        if (start == FieldPosition.MIDDLE) {
            switch (target) {
                case RIGHT: {
                    approachProfile = "rightSwitchFromMiddleStart";
                    exitProfile = "crossLineFromBehindRightSwitch";
                    break;
                }
                case LEFT: {
                    approachProfile = "leftSwitchFromMiddleStart";
                    exitProfile = "crossLineFromBehindLeftSwitch";
                    break;
                }
                default: {
                    approachProfile = null;
                    dropBox = false;
                    exitProfile = null;
                }
            }
        } else if (start == target) {
            switch (target) {
                case RIGHT: {
                    approachProfile = "rightSwitchFromRightStart";
                    exitProfile = "crossLineFromBehindRightSwitch";
                    break;
                }
                case LEFT: {
                    approachProfile = "leftSwitchFromLeftSide";
                    exitProfile = "crossLineFromBehindLeftSwitch";
                    break;
                }
                // This should never happen!
                default: {
                    dropBox = false;
                    approachProfile = null;
                    exitProfile = null;
                    System.err.println("SwitchAuto: start==target but isn't LEFT or RIGHT");
                }
            }
        } else
        {
            dropBox = false;
            switch (start)
            {
                case RIGHT:
                {
                    approachProfile = "crossLineFromRightStart";
                    exitProfile = null;
                    break;
                }
                case LEFT:
                {
                    approachProfile = "crossLineFromLeftStart";
                    exitProfile = null;
                    break;
                }
                default:
                {
                    approachProfile = null;
                    exitProfile = null;
                    System.err.println("SwitchAuto: start wasn't LEFT, MIDDLE, or RIGHT");
                }
            }
        }

        if (approachProfile == null)
        {
            addSequential(new WaitCommand(15));
            return;
        }

        addSequential(new FollowProfile(approachProfile));
        if (dropBox) {
            addSequential(new RunShoulder(0.8), 1); // TODO: Time this to properly drop the box
            addSequential(new ConstantDrive(0.4, 1));
            addSequential(new SetGrabber(Grabber.GrabberState.OPEN));
            addSequential(new WaitCommand(0.5));
            addSequential(new SetGrabber(Grabber.GrabberState.CLOSE));
            addSequential(new RunShoulder(-0.6), 1);
            addSequential(new ConstantDrive(-0.4,2));
        }

        if (exitProfile != null)
        {
            addSequential(new WaitCommand(0.2));
            addSequential(new FollowProfile(exitProfile));
        }
    }
}
