package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunWheels extends Command {

    private double output;
    public boolean isFinished()
    {
        return false;
    }

    public RunWheels(double output)
    {
        requires(Robot.shooter.wheels);
        this.output = output;
    }

    public void execute()
    {
        Robot.shooter.wheels.set(output);
    }

    public void end()
    {
        Robot.shooter.wheels.set(0);
    }

    public void interrupted()
    {
        end();
    }
}
