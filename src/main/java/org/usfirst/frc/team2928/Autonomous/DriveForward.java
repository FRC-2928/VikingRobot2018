package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;

public class DriveForward extends Command {

    private Waypoint[] waypoints;

    public DriveForward(double distance) {
        requires(Robot.drivebase);
        waypoints = new Waypoint[]{
                new Waypoint(0,0,0),
                new Waypoint(0.1, distance, 0)};
    }

    public void initialize() {
        System.out.println("Generating traj");
        Robot.drivebase.setWaypoints(waypoints);
        System.out.println("Traj generated");
    }

    public void execute() {
        Robot.drivebase.trajectoryDrive();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
