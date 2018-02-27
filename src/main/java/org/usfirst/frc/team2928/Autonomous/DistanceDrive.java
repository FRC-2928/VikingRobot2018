package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class DistanceDrive extends Command {
    private int ticks;
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public DistanceDrive(int ticks)
    {
        requires(Robot.drivebase);
        this.ticks = ticks;
    }

    public void initialize()
    {
        //Robot.drivebase.
    }
}
