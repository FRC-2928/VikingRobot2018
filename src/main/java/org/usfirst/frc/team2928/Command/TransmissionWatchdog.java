package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Transmission;

public class TransmissionWatchdog extends Command {
    protected boolean isFinished()
    {
        return false;
    }

    public void initialize()
    {
        requires(Robot.transmission);
    }

    public void execute()
    {
        boolean inHighGear = Robot.transmission.getGear() == Transmission.GearState.HIGH;
        if (Robot.shoulder.getTargetPercentage() >= 0.5 && inHighGear)
        {
            Robot.transmission.shift(Transmission.GearState.LOW);
        }
    }
}
