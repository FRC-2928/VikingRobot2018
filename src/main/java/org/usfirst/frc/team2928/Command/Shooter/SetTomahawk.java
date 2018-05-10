package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Shooter.Tomahawk;

public class SetTomahawk extends Command {
    private Tomahawk.TomahawkState target;

    public SetTomahawk(Tomahawk.TomahawkState target)
    {
        requires(Robot.shooter.tomahawk);
        this.target = target;
    }

    public void initialize()
    {
        Robot.shooter.tomahawk.set(target);
    }

    public boolean isFinished()
    {
        return true;
    }
}
