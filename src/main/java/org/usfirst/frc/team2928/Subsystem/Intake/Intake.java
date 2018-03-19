package org.usfirst.frc.team2928.Subsystem.Intake;

public class Intake {

    public Angle angle;
    public Clamp clamp;
    public Motors motors;

    public Intake()
    {
        angle = new Angle();
        clamp = new Clamp();
        motors = new Motors();
    }
}
