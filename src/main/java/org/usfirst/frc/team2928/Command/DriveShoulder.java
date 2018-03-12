package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class DriveShoulder extends Command {

    private double power;
    public DriveShoulder(double power)
    {
        requires(Robot.shoulder);
        this.power = power;
    }

    @Override
    public void initialize()
    {
        Robot.shoulder.setPower(power);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    public void end()
    {
        Robot.shoulder.setPower(0);
    }

    public void interrupted()
    {
        end();
    }
}
