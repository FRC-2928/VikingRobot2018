package org.usfirst.frc.team2928.Subsystem.Intake;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

    public Angle angle;
    public Clamp clamp;
    public Motors motors;

    public Intake()
    {
        angle = new Angle();
        clamp = new Clamp();
        motors = new Motors();
    }

    @Override
    public void initDefaultCommand()
    {

    }
}
