package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.DriverStation;

public class Match {
    private static Match instance = null;

    public Field.Position nearSwitch;
    public Field.Position scale;
    public Field.Position farSwitch;
    public DriverStation.Alliance alliance;

    protected Match() {
        String fieldState = DriverStation.getInstance().getGameSpecificMessage();
        if (fieldState.length() < 3) {
            fieldState = "EEE";
        }
        nearSwitch = Field.Position.fromLetter(fieldState.charAt(0));
        scale = Field.Position.fromLetter(fieldState.charAt(1));
        farSwitch = Field.Position.fromLetter(fieldState.charAt(2));
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