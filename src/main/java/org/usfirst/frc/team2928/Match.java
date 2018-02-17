package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.DriverStation;

public class Match {
    private static Match instance = null;

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

    public Position nearSwitch;
    public Position scale;
    public Position farSwitch;
    public DriverStation.Alliance alliance;

    protected Match() {
        String fieldState = DriverStation.getInstance().getGameSpecificMessage();
        if (fieldState.length() < 3) {
            fieldState = "EEE";
        }
        nearSwitch = Position.fromLetter(fieldState.charAt(0));
        scale = Position.fromLetter(fieldState.charAt(1));
        farSwitch = Position.fromLetter(fieldState.charAt(2));
        alliance = DriverStation.getInstance().getAlliance();
    }

    public static Match getInstance()
    {
        if (instance == null)
        {
            instance = new Match();
        }
        return instance;
    }
}
