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
        requires(Robot.chassis.drivetrain);
        this.profile = profile;
    }

    public FollowProfile(String profileName)
    {
        requires(Robot.chassis.drivetrain);
        this.profile = new Profile(profileName);
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.resetTalons();
        Robot.chassis.drivetrain.setProfile(profile);
        notifier = NotifierManager.getInstance().runNotifier(
                Robot.chassis.drivetrain::profileDrive,
                (RobotConstants.PROFILE_TICK_MS/1000d)/2d); // Run faster than the profile tick
    }

    @Override
    protected boolean isFinished() {
        return Robot.chassis.drivetrain.doneWithProfile();
    }

    public void end()
    {
        notifier.stop();
        Robot.chassis.drivetrain.resetTalons();
    }
}
