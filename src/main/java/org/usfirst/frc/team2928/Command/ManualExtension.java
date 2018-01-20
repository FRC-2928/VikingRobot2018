package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ManualExtension extends Command {
    private double targetPercentage;
    private double delta;

    private double clamp(double value, double min, double max)
    {
        return Math.max(min, Math.min(max, value));
    }

    public ManualExtension(double delta)
    {
        targetPercentage = 0;
    }

    public void initialize()
    {
        requires(Robot.shoulder);
        targetPercentage = Robot.shoulder.getTargetPercentage();
    }

    public void execute()
    {
        Robot.shoulder.setExtension(clamp(targetPercentage + delta * timeSinceInitialized(), 0, 1));
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
