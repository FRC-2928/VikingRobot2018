package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.TransformableWaypoint;

public class DriveForward extends Command {

    private TransformableWaypoint[] waypoints;

    public DriveForward(double distance) {
        requires(Robot.drivebase);
        waypoints = new TransformableWaypoint[]{
                new TransformableWaypoint(0, 0, 0),
                new TransformableWaypoint(distance, 0, 0)
        };
    }

    public void initialize() {
        Robot.drivebase.setWaypoints(waypoints);
    }

    public void execute() {
        Robot.drivebase.trajectoryDrive();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
