package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.*;

import java.util.ArrayList;
import java.util.List;

public class DriveToTarget extends Command {

    private Field.Position startingPosition;
    private AutoTarget target;

    private Notifier pathfinderNotifier;

    @Override
    public boolean isFinished() {
        return false;
    }

    public void goForSwitch() {
    }

    DriveToTarget(Field.Position start, AutoTarget target) {
        this.startingPosition = start;
        this.target = target;

        pathfinderNotifier = new Notifier(Robot.drivebase::trajectoryDrive);
    }

    private TransformableWaypoint[] generatePath(AutoTarget target) {
        List<TransformableWaypoint> path = new ArrayList<>();

        path.add(Field.Objects.START.getWaypoint(startingPosition));
        if (target == AutoTarget.SWITCH && startingPosition == Match.getInstance().nearSwitch)
            path.addAll(targetSwitch());
        else if (target != AutoTarget.LINE)
            path.addAll(targetScale());
        else
            path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(startingPosition));
        return path.toArray(new TransformableWaypoint[0]);
    }

    private List<TransformableWaypoint> targetSwitch() {
        List<TransformableWaypoint> path = new ArrayList<>();
        path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(startingPosition));
        path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(startingPosition).rotate(Field.sideAngle(90, startingPosition)));
        path.add(Field.Objects.SWITCH.getWaypoint(startingPosition).rotate(Field.sideAngle(90, startingPosition)));
        return path;
    }

    private List<TransformableWaypoint> targetScale() {
        List<TransformableWaypoint> path = new ArrayList<>();
        if (startingPosition != Field.Position.MIDDLE)
            path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint((startingPosition)));
        else
            path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(Match.getInstance().scale));

        path.add(Field.Objects.MIDPOINT.getWaypoint(Match.getInstance().scale));
        path.add(Field.Objects.SCALE_INTERMEDIATE.getWaypoint(Match.getInstance().scale).rotate(Field.sideAngle(90, Match.getInstance().scale)));
        path.add(Field.Objects.SCALE.getWaypoint(Match.getInstance().scale).rotate(Field.sideAngle(90, Match.getInstance().scale)));
        return path;
    }

    public void initialize() {
        pathfinderNotifier.startPeriodic(RobotConstants.PATHFINDER_TIME_INTERVAL);
    }

    public void interrupted() {
        pathfinderNotifier.stop();
    }

    public void end() {
        pathfinderNotifier.stop();
    }
}
