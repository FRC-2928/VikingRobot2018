package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Shoot extends CommandGroup {

    public Shoot()
    {
        addParallel(new RunWheels(1), 2.1);
        addSequential(new WaitCommand(2));
        addSequential(new Flip(), 0.1);
    }
}
