package org.usfirst.frc.team2928.MotionProfiling;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Notifier;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.Subsystem.Chassis.VikingSRX;

public class ProfileFollower {

    private VikingSRX left;
    private VikingSRX right;

    public Notifier processBuffer;
    public Notifier followProfile;

    public ProfileFollower(VikingSRX left, VikingSRX right)
    {
        this.left = left;
        this.right = right;
        processBuffer = new Notifier(this::processMotionProfileBufferPeriodic);
        followProfile = new Notifier(this::followProfilePeriodic);
    }

    public void setProfiles(Profile[] profiles)
    {
        if (profiles[0].size() != profiles[1].size())
            System.err.println("Profile size mismatch, things WILL break.");
        left.profile = profiles[0];
        right.profile = profiles[1];
    }

    public void initFollowProfile()
    {
        for (VikingSRX v : new VikingSRX[] {left, right})
        {
            v.followInit();
            for (int i = 0; i < 64; i++)
                v.sendNextPoint(); // Get some initial points
        }
    }

    public void processMotionProfileBufferPeriodic()
    {
        for (VikingSRX v : new VikingSRX[] {left, right})
        {
            v.sendNextPoint(); // It's ok if this fails, we won't lose any points
            v.processMotionProfileBuffer();
        }
    }

    public void followProfilePeriodic()
    {
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        left.set(ControlMode.MotionProfile, statusL.isLast ? 2 : 1);
        right.set(ControlMode.MotionProfile, statusR.isLast ? 2 : 1);
    }

    public boolean doneWithProfile()
    {
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        return statusL.isLast && statusR.isLast;
    }

    public void startFollowing()
    {
        if (left.profile == null || right.profile == null) {
            System.err.println("Call setProfiles before attempting to follow a profile");
            return;
        }
        initFollowProfile();
        processBuffer.startPeriodic(RobotConstants.PROFILE_TICK_MS/2); // We must process the buffer faster than we execute it
        followProfile.startPeriodic(RobotConstants.PROFILE_TICK_MS);
    }

    public void stopFollowing()
    {
        processBuffer.stop();
        followProfile.stop();
    }
}