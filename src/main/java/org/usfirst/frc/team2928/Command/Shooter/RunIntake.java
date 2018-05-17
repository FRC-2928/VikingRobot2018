package org.usfirst.frc.team2928.Command.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunIntake extends Command {
    private double intake;
    private double wheels;
    public RunIntake(double intake, double wheels)
    {
        requires(Robot.shooter.intake);
        requires(Robot.shooter.wheels);
        this.intake = intake;
        this.wheels = wheels;
    }

    public void initialize()
    {

    }

    public void execute()
    {
        Robot.shooter.intake.set(intake);
        Robot.shooter.wheels.set(wheels);
    }

    public void end()
    {
        Robot.shooter.intake.set(0);
        Robot.shooter.wheels.set(0);
    }

    public void interupted()
    {
        end();
    }

    public boolean isFinished()
    {
        return false;
    }
}
