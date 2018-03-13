package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ResetSensors extends Command {
    public ResetSensors()
    {
        requires(Robot.drivebase);
    }
    @Override
    public boolean isFinished()
    {
        return true;
    }

    public void initialize()
    {
        Robot.drivebase.zeroSensors();
    }
}
