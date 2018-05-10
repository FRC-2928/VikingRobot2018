package org.usfirst.frc.team2928.Subsystem.Shooter;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.Subsystem.Chassis.VikingSRX;

import static org.usfirst.frc.team2928.RobotMap.TALON_LIFTER;

public class Lifter extends Subsystem {

    VikingSRX lifter;
    public void initDefaultCommand()
    {
        return;
    }

    public Lifter()
    {
        lifter = new VikingSRX(TALON_LIFTER);
    }

    public void set(double output)
    {
        lifter.set(output);
    }
}
