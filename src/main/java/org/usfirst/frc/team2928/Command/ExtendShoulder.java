package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ExtendShoulder extends Command {
    private double target;

    public ExtendShoulder(double target)
    {
        this.target = target;
        requires(Robot.shoulder);
    }

    public void execute()
    {
        Robot.shoulder.setExtension(target);
    }

    // TODO: Ensure that we only need to set the setpoint once
    @Override
    public boolean isFinished()
    {
        return true;
    }
}