package org.usfirst.frc.team2928.Command.Intake;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunAngle extends Command {

    private double power;

    public RunAngle(double power){
        requires(Robot.intake.angle);
        this.power = power;
    }

    public void initialize()
    {
        Robot.intake.angle.setPower(power);
    }

    public void end()
    {
        Robot.intake.angle.setPower(0);
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