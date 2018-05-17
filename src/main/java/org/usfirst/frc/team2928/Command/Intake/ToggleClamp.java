package org.usfirst.frc.team2928.Command.Intake;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleClamp extends Command {

    public ToggleClamp()
    {
        requires(Robot.intake.clamp);
    }

    public void initialize()
    {
        Robot.intake.clamp.toggle();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
