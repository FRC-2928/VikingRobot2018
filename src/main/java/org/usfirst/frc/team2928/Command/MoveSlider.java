package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

import static org.usfirst.frc.team2928.Robot.*;

public class MoveSlider extends Command {

    private double power;
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public MoveSlider(double power)
    {
        requires(Robot.slider);
        this.power = power;
    }

    public void initialize()
    {
        Robot.slider.setSpeed(-power);
    }

    public void end()
    {
        Robot.slider.setSpeed(0);
    }
}
