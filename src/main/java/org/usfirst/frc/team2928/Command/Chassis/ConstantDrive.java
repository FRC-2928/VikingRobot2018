package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class ConstantDrive extends Command {

    private double power;
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public ConstantDrive(double power)
    {
        requires(Robot.chassis.drivetrain);
        this.power = power;
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.drive(power, 0, false);
    }

    public void end()
    {
        Robot.chassis.drivetrain.drive(0,0);
    }

    public void interrupted()
    {
        end();
    }
}