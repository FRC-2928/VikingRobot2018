package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.TransformableWaypoint;

public class DriveForward extends Command {

    private TransformableWaypoint[] waypoints;
    Notifier driveForwardNotifer;

    public DriveForward(double distance) {
        requires(Robot.drivebase);
        driveForwardNotifer = new Notifier(Robot.drivebase::trajectoryDrive);
        waypoints = new TransformableWaypoint[]{
                new TransformableWaypoint(0, 0, 0),
                new TransformableWaypoint(distance, 0, 0)
        };
    }

    public void initialize() {
        Robot.drivebase.setWaypoints(waypoints);
        driveForwardNotifer.startPeriodic(RobotConstants.PATHFINDER_TIME_INTERVAL);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void interrupted() {
        driveForwardNotifer.stop();
    }

    public void end() {
        driveForwardNotifer.stop();
    }
}
