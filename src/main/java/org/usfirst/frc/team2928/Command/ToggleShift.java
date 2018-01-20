package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Transmission.GearState;

public class ToggleShift extends Command {
    private GearState target;
    public void initialize()
    {
        requires(Robot.transmission);
        target = Robot.transmission.getGear().toggle();
    }

    public void execute()
    {
        Robot.transmission.shift(target);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
