package org.usfirst.frc.team2928.Command.Arm;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunSlider extends Command {

    private double power;
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public RunSlider(double power)
    {
        requires(Robot.arm.slider);
        this.power = power;
    }

    public void initialize()
    {
        Robot.arm.slider.setPower(power);
    }

    public void end()
    {
        Robot.arm.slider.setPower(0);
    }
}