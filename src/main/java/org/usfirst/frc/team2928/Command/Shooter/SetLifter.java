package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Shooter.Tomahawk;

public class SetLifter extends Command {

    private double target;

    private Tomahawk.TomahawkState tomahawkStateAtInit;
    public SetLifter(double percent)
    {
        requires(Robot.shooter.lifter);
        requires(Robot.shooter.tomahawk);
        target = percent;
    }

    public void initialize()
    {
        tomahawkStateAtInit = Robot.shooter.tomahawk.get();
        if (target > 0.4)
        {
            Robot.shooter.tomahawk.set(Tomahawk.TomahawkState.RAISE);
        }
    }

    public void execute()
    {
        if (target > 0.4 && timeSinceInitialized() < 0.25 && tomahawkStateAtInit == Tomahawk.TomahawkState.LOWER)
            return;
        Robot.shooter.lifter.setPercent(target);
    }

    public boolean isFinished()
    {
        // We should actually be checking the closed loop error, but all this was written in 20 minutes.
        return timeSinceInitialized() > 5;
    }

    public void end()
    {
        Robot.shooter.lifter.set(0);
    }

    public void interrupted()
    {
        end();
    }
}
