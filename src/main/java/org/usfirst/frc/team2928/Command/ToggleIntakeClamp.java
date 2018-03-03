package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleIntakeClamp extends Command {

    public ToggleIntakeClamp()
    {
        requires(Robot.intakeClamp);
    }

    public void initialize()
    {
        Robot.intakeClamp.toggle();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
