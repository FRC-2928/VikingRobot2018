package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunIntake extends Command {
    private double power;

    @Override
    public boolean isFinished() {
        return false;
    }

    public RunIntake(double power) {
        requires(Robot.intake);
        this.power = power;
    }

    public void initialize() {
        Robot.intake.setIntake(power);
    }

    public void execute() {
    }

    public void end() {
        Robot.intake.setIntake(0.0);
    }
}
