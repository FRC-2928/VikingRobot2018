package org.usfirst.frc.team2928;

import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team2928.Autonomous.TransformableWaypoint;
import org.usfirst.frc.team2928.Subsystem.Transmission;

public class Field {

    public enum Position
    {
        LEFT,
        MIDDLE,
        RIGHT,
        ERROR;

        static Position fromLetter(char l)
        {
            switch(l)
            {
                case 'L' : return LEFT;
                case 'M' : return MIDDLE;
                case 'R' : return RIGHT;
                case 'E' : return ERROR;
                default: return ERROR;
            }
        }
    }

    public enum Objects
    {
        SWITCH                      (new TransformableWaypoint(0,0), false),
        SWITCH_INTERMEDIATE         (new TransformableWaypoint(0,0), false),
        LINE_INTERMEDIATE_CLOSE     (new TransformableWaypoint(0,0), false),
        LINE_INTERMEDIATE_FAR       (new TransformableWaypoint(0,0), false),
        SCALE                       (new TransformableWaypoint(0,0), false),
        SCALE_INTERMEDIATE          (new TransformableWaypoint(0,0), false),
        BOXES                       (new TransformableWaypoint(0,0), true),
        START                       (new TransformableWaypoint(0,0), false); // START is a special case

        private TransformableWaypoint waypoint;
        private boolean centered;
        Objects(TransformableWaypoint waypoint, boolean centered)
        {
            this.waypoint = waypoint;
        }

        public TransformableWaypoint getWaypoint() {
            if (this.centered)
                return new TransformableWaypoint(waypoint);
            else if (this.equals(START)) // Our special case, START can be centered or on the side
                return START.getWaypoint().midpoint(START.getWaypoint().flipSide());
            throw new IllegalArgumentException("Non-centered waypoints require a non-middle side, try getWaypoint(Field.Position) instead.");
        }

        public TransformableWaypoint getWaypoint(Position side)
        {
            if (side.equals(Position.ERROR))
                throw new IllegalArgumentException("side cannot be ERROR!");
            if (side.equals(Position.MIDDLE))
                return this.getWaypoint();
            if (!this.centered)
            {
                if (side.equals(Position.LEFT))
                    return new TransformableWaypoint(waypoint);
                else
                    return this.waypoint.flipSide();
            }
            throw new IllegalArgumentException("Centered waypoints cannot take a side! Try getWaypoint() instead.");
        }
    }
}
