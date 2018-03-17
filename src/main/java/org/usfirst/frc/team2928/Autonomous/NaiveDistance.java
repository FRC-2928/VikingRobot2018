package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;

public class NaiveDistance extends Command {

    private int ticks;

    public NaiveDistance(double feet)
    {
        requires(Robot.chassis.drivetrain);
        this.ticks = (int)Conversions.FeetToTicks(feet, Transmission.GearState.HIGH);
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.drive(0.4 * Math.copySign(0.4, ticks), 0, false);
    }

    @Override
    public boolean isFinished()
    {
        int[] encoders = Robot.chassis.drivetrain.getEncoders();
        return Math.abs(encoders[0] + encoders[1]) >= Math.abs(2 * ticks);
    }

    public void end()
    {
        Robot.chassis.drivetrain.drive(0,0,false);
    }

    public void interrupted()
    {
        end();
    }
}
