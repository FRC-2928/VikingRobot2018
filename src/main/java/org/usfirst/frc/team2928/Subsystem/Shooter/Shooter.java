package org.usfirst.frc.team2928.Subsystem.Shooter;

public class Shooter {
    public Flipper flipper;
    public Intake intake;
    public Wheels wheels;
    public Lifter lifter;
    public Tomahawk tomahawk;

    public Shooter()
    {
        this.flipper = new Flipper();
        this.intake = new Intake();
        this.wheels = new Wheels();
        this.tomahawk = new Tomahawk();
        this.lifter = new Lifter();
    }
}
