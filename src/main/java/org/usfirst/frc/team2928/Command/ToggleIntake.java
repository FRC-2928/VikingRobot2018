package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ToggleIntake extends Command {

    public ToggleIntake()
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
