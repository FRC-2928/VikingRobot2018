package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission.GearState;

public class Shift extends Command {

    private GearState target;

    public Shift(GearState target)
    {
        requires(Robot.chassis.transmission);
        this.target = target;
    }

    public void execute()
    {
        Robot.chassis.transmission.shift(target);
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}