package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Field;

public class WaitForFMSData extends Command {

    public WaitForFMSData()
    {
        // No subsystem requirements
    }

    public void initialize()
    {
        // Nothing to initialize
    }

    public void execute()
    {
        // Nothing to execute
    }

    @Override
    public boolean isFinished()
    {
        // Better to put this in isFinished, as it's called just as often as execute
        return Field.getInstance().update();
    }
}
