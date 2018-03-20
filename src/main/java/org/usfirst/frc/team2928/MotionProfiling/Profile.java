package org.usfirst.frc.team2928.MotionProfiling;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;

import java.util.List;

public class Profile {
    private List<List<Double>> pointData;
    public Profile(List<List<Double>> pointData)
    {
        this.pointData = pointData;
    }

    public TrajectoryPoint getPoint(int index)
    {
        TrajectoryPoint point = new TrajectoryPoint();
        if (index >= pointData.size() || index < 0) {
            System.err.println("Attempted to get a point that doesn't exist!");
            return null;
        }
        point.position = RobotConstants.DRIVE_TICKS_PER_FOOT * pointData.get(index).get(0);
        point.velocity = RobotConstants.DRIVE_TICKS_PER_FOOT * pointData.get(index).get(0)/10;
        point.headingDeg = 0;
        point.profileSlotSelect0 = 0;
        point.profileSlotSelect1 = 0;
        point.timeDur = GetTrajectoryDuration((int)pointData.get(index).get(2).doubleValue());
        point.zeroPos = index == 0;
        point.isLastPoint = index == (pointData.size() - 1);
        System.out.println("Created point");
        return point;
    }

    private TrajectoryPoint.TrajectoryDuration GetTrajectoryDuration(int durationMs) {
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
	
    public int size()
    {
        return pointData.size();
    }
}
