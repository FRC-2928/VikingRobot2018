package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Command.OneShotCommand;
import org.usfirst.frc.team2928.Robot;

public class Calibrate extends OneShotCommand {

    public Calibrate()
    {
        super(Robot.shooter.lifter::calibrate, Robot.shooter.lifter);
    }
}
