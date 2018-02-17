package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Robot;

public class Rotate extends Command {

    private Waypoint[] trajectory;

    @Override
    public boolean isFinished() {
        return Robot.drivebase.doneWithTrajectory();
    }

    public Rotate(double angle) {
        trajectory = new Waypoint[]{new Waypoint(0, 0, angle)};
    }

    public void initialize() {
        requires(Robot.drivebase);
        Robot.drivebase.setWaypoints(trajectory);
    }

    public void execute() {
        Robot.drivebase.trajectoryDrive();
    }

}
