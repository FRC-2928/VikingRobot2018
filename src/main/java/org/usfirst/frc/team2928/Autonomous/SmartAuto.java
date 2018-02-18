package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Field;
import org.usfirst.frc.team2928.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static org.usfirst.frc.team2928.Field.Position.LEFT;
import static org.usfirst.frc.team2928.Field.Position.MIDDLE;
import static org.usfirst.frc.team2928.Field.Position.RIGHT;

public class SmartAuto extends Command
{
    public enum AutoTarget
    {
        SCALE,
        SWITCH,
        LINE
    }

    private double sideAngle(double leftAngle)
    {
        return leftAngle * (startingPosition.equals(LEFT) ? 1 : -1);
    }

    private Field.Position startingPosition;
    private AutoTarget target;

    // If we're in the middle, do our alliance partners want us to go to the inside or outside?
    private boolean innerLeft;
    private boolean innerRight;

    @Override
    public boolean isFinished()
    {
        return false;
    }

    public void goForSwitch()
    {
    }

    public SmartAuto(Field.Position start, AutoTarget target, boolean innerLeft, boolean innerRight)
    {
        this.startingPosition = start;
        this.target = target;
        this.innerLeft = innerLeft;
        this.innerRight = innerRight;
    }

    private TransformableWaypoint[] generatePath(AutoTarget target)
    {
        throw new NotImplementedException();
    }

    private List<TransformableWaypoint> targetSwitch()
    {
        List<TransformableWaypoint> path = new ArrayList<>();

        if (startingPosition == Match.getInstance().nearSwitch)
        {
            path.add(Field.Objects.SWITCH_INTERMEDIATE.getWaypoint(startingPosition));
            path.add(new TransformableWaypoint(0,0, sideAngle(90)));
        }

        throw new NotImplementedException();
    }
}
