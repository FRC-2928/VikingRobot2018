package org.usfirst.frc.team2928.Subsystem.Arm;

public class Arm {
    public Grabber grabber;
    public Shoulder shoulder;
    public Slider slider;
    public Climber climber;

    public Arm()
    {
        grabber = new Grabber();
        shoulder = new Shoulder();
        slider = new Slider();
        climber = new Climber();
    }
}
