package org.usfirst.frc.team2928.Command.Intake;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

public class SetClamp extends Command {

    private Clamp.ClampState target;

    public SetClamp(Clamp.ClampState target)
    {
        requires(Robot.intake.clamp);
        this.target = target;
    }

    public void initialize()
    {
        Robot.intake.clamp.set(target);
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
