package org.usfirst.frc.team2928.Command.Intake;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class RunMotors extends Command {
    private double power;

    @Override
    public boolean isFinished() {
        return false;
    }

    public RunMotors(double power) {
        requires(Robot.intake.motors);
        this.power = power;
    }

    public void initialize() {
        Robot.intake.motors.set(power);
    }

    public void execute() {
    }

    public void end() {
        Robot.intake.motors.set(0d);
    }
}