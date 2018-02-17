package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Match;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

public class SmartAuto extends Command
{
    public enum AutoTarget
    {
        SCALE,
        SWITCH,
        LINE
    }

    private Match.Position startingPosition;
    private AutoTarget target;

    @Override
    public boolean isFinished()
    {
        return false;
    }

    public void goForSwitch()
    {
    }

    public SmartAuto(Match.Position start, AutoTarget target)
    {
        this.startingPosition = start;
        this.target = target;
    }

    private TransformableWaypoint[] generatePath(AutoTarget target)
    {

    }

    private List<TransformableWaypoint> targetSwitch()
    {
        List<TransformableWaypoint> path = new ArrayList<>();

        if (startingPosition == Match.getInstance().nearSwitch || startingPosition == Match.Position.MIDDLE)
        {
            path.add(Waypoints.)
        }
    }
}
