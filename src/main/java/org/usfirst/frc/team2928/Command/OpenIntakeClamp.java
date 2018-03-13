package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class OpenIntakeClamp extends Command {

    public OpenIntakeClamp()
    {
        requires(Robot.intakeClamp);

    }
    @Override
    public boolean isFinished()
    {
        return true;
    }

    public void initialize()
    {
        Robot.intakeClamp.open();
    }

}
