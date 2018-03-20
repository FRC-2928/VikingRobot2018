package org.usfirst.frc.team2928.MotionProfiling;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class FollowProfile extends Command {

    private Profile[] profiles;

    public FollowProfile(String profileName) {
        requires(Robot.chassis.drivetrain);
        ProfileLoader profileLoader = new ProfileLoader();
        profiles = profileLoader.loadProfile(profileName);
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.setProfiles(profiles);
        Robot.chassis.drivetrain.startProfileDrive();
    }

    // We don't need an execute method because all the profile following happens in Notifiers, on a different thread

    @Override
    public boolean isFinished()
    {
        return Robot.chassis.drivetrain.doneWithProfile();
    }

    public void end()
    {
        Robot.chassis.drivetrain.stopProfileDrive();
    }

    public void interrupted()
    {
        end();
    }
}
