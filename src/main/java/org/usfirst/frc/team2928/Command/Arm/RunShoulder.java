package org.usfirst.frc.team2928.Command.Arm;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunShoulder extends Command {

    private double power;
    public RunShoulder(double power)
    {
        requires(Robot.arm.shoulder);
        this.power = power;
    }

    @Override
    public void initialize()
    {
        Robot.arm.shoulder.setPower(power);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    public void end()
    {
        Robot.arm.shoulder.setPower(0);
    }

    public void interrupted()
    {
        end();
    }
}
