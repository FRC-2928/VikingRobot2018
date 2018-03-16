package org.usfirst.frc.team2928.Autonomous;

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


    List<List<Double>> leftProfile;
    List<List<Double>> rightProfile;
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

    public void feedTalons(WPI_TalonSRX left, WPI_TalonSRX right)
    {
        feedTalon(left, leftProfile);
        feedTalon(right, rightProfile);
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

    private void feedTalon(WPI_TalonSRX talon, List<List<Double>> profile)
    {
        TrajectoryPoint point = new TrajectoryPoint();

        talon.clearMotionProfileTrajectories();
        talon.configMotionProfileTrajectoryPeriod(50, RobotConstants.CAN_TIMEOUT_MS);
        for (int i = 0; i < profile.size(); i++)
        {
            point.position = Conversions.FeetToTicks(profile.get(i).get(0), Transmission.GearState.LOW);
            point.velocity = Conversions.FeetToTicks(profile.get(i).get(1), Transmission.GearState.LOW)/10d;
            point.headingDeg = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = GetTrajectoryDuration((int)profile.get(i).get(2).doubleValue());
            point.zeroPos = i == 0;
            point.isLastPoint = i == (profile.size() - 1);
            talon.pushMotionProfileTrajectory(point);
        }
    }
}
