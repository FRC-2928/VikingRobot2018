package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;

public class DriveForward extends Command {

    Waypoint[] waypoints;
    public DriveForward(double distance)
    {
        waypoints = new Waypoint[] {new Waypoint(0, distance, 0)};
        requires(Robot.drivebase);
    }

    public void initialize()
    {
        Robot.drivebase.setWaypoints(waypoints);
    }

    public void execute()
    {
        Robot.drivebase.trajectoryDrive();
    }
    @Override
    protected boolean isFinished() {
        return Robot.drivebase.doneWithTrajectory();
    }
}
