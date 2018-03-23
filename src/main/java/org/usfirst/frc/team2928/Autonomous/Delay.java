package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Delay extends CommandGroup {
    public Delay(double delay, Command after)
    {
        addSequential(new WaitCommand(delay));
        addSequential(after);
    }

    public Delay(double delay, Command after, double timeout)
    {
        addSequential(new WaitCommand(delay));
        addSequential(after, timeout);
    }
}
