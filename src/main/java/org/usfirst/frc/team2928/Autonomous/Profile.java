package org.usfirst.frc.team2928.Autonomous;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Profile {

    // Starting positions - sides: edge of robot 2.5 feet away from corner, middle: dead center
    // Robot is 37in across, so 1.54ft offset on X when generating paths
    // Parameters for pathfinder are 0.05 timestep, 4ft/s, 3ft/s^2, 60 jerk, 1.875 wheelbase W, cubic interpolation

    List<List<Double>> leftProfile;
    List<List<Double>> rightProfile;

    private int leftPointsSent;
    private int rightPointsSent;

    public Profile(String profileName)
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try
        {
        InputStream leftStream = loader.getResource("profile/" + profileName + "_left.csv").openStream();
        InputStream rightStream = loader.getResource("profile/" + profileName + "_right.csv").openStream();
        BufferedReader left = new BufferedReader(new InputStreamReader(leftStream));
        BufferedReader right = new BufferedReader(new InputStreamReader(rightStream));


        leftProfile = fillProfile(left);
        rightProfile = fillProfile(right);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (leftProfile.size() != rightProfile.size())
            System.err.println("Profile mismatch! Will likely crash!");
        leftPointsSent = 0;
        rightPointsSent = 0;

    }

    private List<List<Double>> fillProfile(BufferedReader br)
    {
        List<List<Double>> profile = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null)
            {
                profile.add(new ArrayList<>());
                String[] values = line.split(",");
                if (values[0] == null || values[0] == " ")
                    continue;
                for (int i = 0; i < 3; i++)
                    profile.get(profile.size() - 1).add(Double.parseDouble(values[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }

    private TrajectoryPoint.TrajectoryDuration GetTrajectoryDuration(int durationMs)
    {
        // Shamelessly stolen from https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionProfile/src/org/usfirst/frc/team217/robot/MotionProfileExample.java
		/* create return value */
        TrajectoryPoint.TrajectoryDuration retval = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
        retval = retval.valueOf(durationMs);
		/* check that it is valid */
        if (retval.value != durationMs) {
            DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
        }
		/* pass to caller */
        return retval;
    }

    public void sendNextPoint(WPI_TalonSRX left, WPI_TalonSRX right)
    {
        if (leftPointsSent < leftProfile.size())
        {
            leftPointsSent = sendNextPoint(left, leftProfile, leftPointsSent);
        }

        if (rightPointsSent < rightProfile.size())
        {
            rightPointsSent = sendNextPoint(right, rightProfile, rightPointsSent);
        }

    }

    private int sendNextPoint(WPI_TalonSRX talon, List<List<Double>> profile, int pointsSent)
    {
        TrajectoryPoint point = new TrajectoryPoint();
        if (pointsSent >= leftProfile.size())
            return pointsSent;
        updatePoint(point, profile.get(pointsSent), pointsSent == 0, pointsSent >= profile.size());
        if (talon.pushMotionProfileTrajectory(point) == ErrorCode.OK)
            return pointsSent + 1;
        return pointsSent;
    }
    private void updatePoint(TrajectoryPoint point, List<Double> pointData, boolean firstPoint, boolean lastPoint)
    {
        point.position = Conversions.FeetToTicks(pointData.get(0), Transmission.GearState.LOW);
        point.velocity = Conversions.FeetToTicks(pointData.get(1), Transmission.GearState.LOW)/10d; // Ticks per 100ms
        point.headingDeg = 0;
        point.profileSlotSelect0 = 0;
        point.profileSlotSelect1 = 0;
        point.timeDur = GetTrajectoryDuration((int)pointData.get(2).doubleValue());
        point.zeroPos = firstPoint;
        point.isLastPoint = lastPoint;
    }
}
