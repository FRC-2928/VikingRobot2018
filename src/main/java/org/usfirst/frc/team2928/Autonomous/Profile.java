package org.usfirst.frc.team2928.Autonomous;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team2928.RobotConstants;

import java.io.*;
import java.nio.Buffer;
import java.util.Random;

public class Profile {


    double[][] leftProfile;
    double[][] rightProfile;
    public Profile(String profileName)
    {
        ClassLoader loader = getClass().getClassLoader();
        File leftFile = new File(loader.getResource("/profile/" + profileName + "_left.csv").getFile());
        File rightFile = new File (loader.getResource("/profile/" + profileName + "_right.csv").getFile());
        RandomAccessFile left;
        RandomAccessFile right;

        try {
            left = new RandomAccessFile(leftFile, "r");
            right = new RandomAccessFile(rightFile, "r");
        } catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }

        leftProfile = fillProfile(left);
        rightProfile = fillProfile(right);


    }

    private double[][] fillProfile(RandomAccessFile f)
    {
        String line;
        int lines = 0;
        try {
            while(f.readLine() != null)
                lines++;
            f.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[][] profile = new double[lines][3];
        int currentLine = 0;
        try {
            while ((line = f.readLine()) != null)
            {
                String[] values = line.split(",");
                if (values[0] == null || values[0] == " ")
                    continue;
                for (int i = 0; i < 3; i++)
                    profile[currentLine][i] = Double.parseDouble(values[i]);
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
            DriverStation.getInstance().reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
        }
		/* pass to caller */
        return retval;
    }

    private void feedTalon(WPI_TalonSRX talon, double[][] profile)
    {
        TrajectoryPoint point = new TrajectoryPoint();

        talon.clearMotionProfileTrajectories();
        talon.configMotionProfileTrajectoryPeriod(50, RobotConstants.CAN_TIMEOUT_MS);
        for (int i = 0; i < profile.length; i++)
        {
            point.position = profile[i][0] * RobotConstants.DRIVE_TICKS_PER_ROTATION;
            point.velocity = profile[i][1] * RobotConstants.DRIVE_TICKS_PER_ROTATION / 600d;
            point.headingDeg = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = GetTrajectoryDuration((int)profile[i][2]);
            point.zeroPos = i == 0;
            point.isLastPoint = i == (profile.length - 1);
            talon.pushMotionProfileTrajectory(point);
        }
    }
}
