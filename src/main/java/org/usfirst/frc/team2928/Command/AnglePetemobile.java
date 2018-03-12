package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class AnglePetemobile extends Command {

    private double power;

    public AnglePetemobile(double power){
        requires(Robot.petemobile);
        this.power = power;
    }

    public void initialize()
    {
        Robot.petemobile.setPower(power);
    }

    public void end()
    {
        Robot.petemobile.setPower(0);
    }

    public void interrupted()
    {
        end();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
