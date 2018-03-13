package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.NotifierManager;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.TransformableWaypoint;

public class DriveForward extends Command {

    private Notifier notifier;
    private TransformableWaypoint[] waypoints;

    public DriveForward(double distance) {
        requires(Robot.drivebase);
        waypoints = new TransformableWaypoint[]{
                new TransformableWaypoint(0, 0, 0),
                new TransformableWaypoint(distance, 0, -1)
        };
    }

    public void initialize() {
        Robot.drivebase.setWaypoints(waypoints);
        notifier = NotifierManager.getInstance().runNotifer(Robot.drivebase::trajectoryDrive, RobotConstants.PATHFINDER_TIME_INTERVAL);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void interrupted() {
        notifier.stop();
    }

    public void end() {
        notifier.stop();
    }
}
