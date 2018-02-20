package org.usfirst.frc.team2928;

import jaci.pathfinder.Waypoint;

public class TransformableWaypoint extends Waypoint {

    public static final double fieldWidth = 0;
    public TransformableWaypoint(double x, double y, double angle)
    {
        super(x, y, angle);
    }

    public TransformableWaypoint(double x, double y)
    {
        super(x, y, 0);
    }

    public TransformableWaypoint(Waypoint waypoint)
    {
        super(waypoint.x, waypoint.y, waypoint.angle);
    }

    public TransformableWaypoint transform(double x, double y, double angle)
    {
        return new TransformableWaypoint(this.x + x, this.y + y, this.angle + angle);
    }

    public TransformableWaypoint shift(double x, double y)
    {
        return transform(x, y, 0);
    }

    public TransformableWaypoint rotate(double angle)
    {
        return transform(0, 0, angle);
    }

    public TransformableWaypoint offsetFromStartingPosition(TransformableWaypoint start)
    {
        return shift(-start.x, -start.y);
    }

    public TransformableWaypoint flipSide()
    {
        return new TransformableWaypoint(fieldWidth - this.x, this.y, this.angle);
    }

    public TransformableWaypoint midpoint(TransformableWaypoint other)
    {
        return new TransformableWaypoint((this.x + other.x)/2d, (this.y + other.y)/2d, (this.angle + other.angle)/2d);
    }
}