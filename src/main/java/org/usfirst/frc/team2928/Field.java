package org.usfirst.frc.team2928;

import static org.usfirst.frc.team2928.Field.Position.LEFT;

public class Field {

    public static double sideAngle(double leftAngle, Position position)
    {
        return leftAngle * (position.equals(LEFT) ? 1 : -1);
    }

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
                case 'E' :
                default: return ERROR;
            }
        }
    }


    public enum Objects
    {
        SWITCH                      (new TransformableWaypoint(4.26,1.75), false),
        SWITCH_INTERMEDIATE         (new TransformableWaypoint(4.26,0.77), false),
        SCALE                       (new TransformableWaypoint(0,0), false),
        SCALE_INTERMEDIATE          (new TransformableWaypoint(0,0), false),
        BOXES                       (new TransformableWaypoint(0,0), true),
        MIDPOINT                    (new TransformableWaypoint(5.85, 1.75), false),
        START                       (new TransformableWaypoint(0.42,1.45), false); // START is a special case

        private TransformableWaypoint waypoint;
        private boolean centered;
        Objects(TransformableWaypoint waypoint, boolean centered)
        {
            this.waypoint = waypoint;
            this.centered = centered;
        }

        public TransformableWaypoint getWaypoint() {
            if (this.centered)
                return new TransformableWaypoint(waypoint);
            else if (this.equals(START)) // Our special case, START can be centered or on the side
                return START.getWaypoint(Position.LEFT).midpoint(START.getWaypoint(Position.RIGHT));
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
