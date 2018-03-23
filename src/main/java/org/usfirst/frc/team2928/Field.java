package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.DriverStation;

public class Field {
    public enum FieldPosition {
        LEFT,
        MIDDLE,
        RIGHT;

        public static FieldPosition fromChar(char c)
        {
            switch (Character.toLowerCase(c))
            {
                case 'L': return LEFT;
                case 'R': return RIGHT;
                case 'M': return MIDDLE;
                default: return null;
            }
        }
    }
    private static Field instance;
    private FieldPosition nearSwitch;
    private FieldPosition scale;
    private FieldPosition farSwitch;


    private Field()
    {
    }

    public static Field getInstance()
    {
        if (instance == null)
            instance = new Field();
        return instance;
    }

    public FieldPosition getNearSwitch() {
        return nearSwitch;
    }

    public FieldPosition getScale() {
        return scale;
    }

    public FieldPosition getFarSwitch() {
        return farSwitch;
    }

    public boolean update()
    {
        String fmsData = DriverStation.getInstance().getGameSpecificMessage();
        if (fmsData == null || fmsData.length() < 3)
            return false;
        nearSwitch = FieldPosition.fromChar(fmsData.charAt(0));
        scale = FieldPosition.fromChar(fmsData.charAt(1));
        farSwitch = FieldPosition.fromChar(fmsData.charAt(2));
        return nearSwitch != null && scale != null && farSwitch != null;
    }
}
