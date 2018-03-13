package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class CloseIntakeClamp extends Command {

    public CloseIntakeClamp()
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
        Robot.intakeClamp.close();
    }

}
