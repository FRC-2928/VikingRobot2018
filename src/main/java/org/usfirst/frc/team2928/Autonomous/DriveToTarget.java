package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.Match;
import org.usfirst.frc.team2928.TransformableWaypoint;

import java.util.ArrayList;
import java.util.List;

public class DriveToTarget extends Command {

    private Field.Position startingPosition;
    private AutoTarget target;

    @Override
    public boolean isFinished() {
        return false;
    }

    public void goForSwitch() {
    }

    DriveToTarget(Field.Position start, AutoTarget target) {
        this.startingPosition = start;
        this.target = target;
    }

    private TransformableWaypoint[] generatePath(AutoTarget target) {
        List<TransformableWaypoint> path = new ArrayList<>();

        if (target == AutoTarget.SWITCH && startingPosition == Match.getInstance().nearSwitch)
            path.addAll(targetSwitch());
        else if (target != AutoTarget.LINE)
            path.addAll(targetScale());
        else
            path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(startingPosition));

        for (int i = 0; i < path.size(); i++)
            path.set(i, path.get(i).offsetFromStartingPosition(Field.Objects.START.getWaypoint(startingPosition)));
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
}
