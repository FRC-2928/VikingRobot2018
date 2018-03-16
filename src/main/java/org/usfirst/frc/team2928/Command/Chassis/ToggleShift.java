package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission.GearState;

public class ToggleShift extends Command {
    private GearState target;
    public ToggleShift()
    {
        requires(Robot.chassis.transmission);
    }
    public void initialize()
    {
        Robot.chassis.transmission.toggle();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}