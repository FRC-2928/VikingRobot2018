package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Transmission.GearState;

public class Shift extends Command {

    private GearState target;

    public Shift(GearState target)
    {
        this.target = target;
    }

    public void initialize()
    {
        requires(Robot.transmission);
    }

    public void execute()
    {
        Robot.transmission.shift(target);
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
