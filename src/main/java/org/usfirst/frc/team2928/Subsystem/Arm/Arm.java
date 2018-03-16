package org.usfirst.frc.team2928.Subsystem.Arm;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
    public Grabber grabber;
    public Shoulder shoulder;
    public Slider slider;

    public Arm()
    {
        grabber = new Grabber();
        shoulder = new Shoulder();
        slider = new Slider();
    }

    @Override
    public void initDefaultCommand()
    {

    }
}
