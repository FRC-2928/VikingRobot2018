package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class Rotate extends Command {

    private double degrees;

    public Rotate(double degrees) {
        requires(Robot.chassis.drivetrain);
        this.degrees = degrees;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

    }

    @Override
    public void interrupted(){

    }

    @Override
    public void end(){

    }
}