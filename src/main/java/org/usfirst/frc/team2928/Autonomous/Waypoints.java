package org.usfirst.frc.team2928.Autonomous;
import java.util.HashMap;
import java.util.Map;

public class Waypoints {
    private static final Map<String, TransformableWaypoint> leftWaypoints = new HashMap<>();
    static
    {
        leftWaypoints.put("nearSwitch", new TransformableWaypoint(0,0));
        leftWaypoints.put("nearSwitchIntermediate", new TransformableWaypoint(0, 0));
        leftWaypoints.put("lineIntermediateClose", new TransformableWaypoint(0,0));
        leftWaypoints.put("lineIntermediateFar", new TransformableWaypoint(0,0));
        leftWaypoints.put("scale", new TransformableWaypoint(0,0));
        leftWaypoints.put("scaleIntermediate", new TransformableWaypoint(0,0));
        leftWaypoints.put("boxesCenter", new TransformableWaypoint(0,0));
        leftWaypoints.put("start", new TransformableWaypoint(0,0));
        leftWaypoints.put("startMiddle", new TransformableWaypoint(0, 0));
    }

    public static TransformableWaypoint getWaypoint(String location)
    {

    }

}
