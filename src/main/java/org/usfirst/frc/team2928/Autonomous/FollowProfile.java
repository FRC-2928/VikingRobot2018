package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.NotifierManager;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;

public class FollowProfile extends Command {

    private Profile profile;
    private Notifier notifier;
    public FollowProfile(Profile profile)
    {
        requires(Robot.drivebase);
        this.profile = profile;
    }

    public FollowProfile(String profileName)
    {
        requires(Robot.drivebase);
        this.profile = new Profile(profileName);
    }

    public void initialize()
    {
        profile.feedTalons(Robot.drivebase.left, Robot.drivebase.right);
        notifier = NotifierManager.getInstance().runNotifier(
                Robot.drivebase::profileDrive,
                (RobotConstants.PROFILE_TICK_MS/1000)/2); // No clue why we /2 it, but the manual says to.
    }

    @Override
    protected boolean isFinished() {
        return Robot.drivebase.doneWithProfile();
    }

    public void end()
    {

        Robot.drivebase.resetTalons();
    }
}
