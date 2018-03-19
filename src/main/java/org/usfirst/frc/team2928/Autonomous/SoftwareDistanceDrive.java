package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;

public class SoftwareDistanceDrive extends PIDCommand {
    private int ticks;
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public SoftwareDistanceDrive(int feet)
    {
        super(0.00001, 0.0000003, 0);
        requires(Robot.chassis.drivetrain);
        this.ticks = (int)Conversions.FeetToTicks(feet, Transmission.GearState.LOW);
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.zeroSensors();
        setSetpoint(ticks);
    }

    public double returnPIDInput()
    {
        int[] encoders = Robot.chassis.drivetrain.getEncoderPositions();
        return ((double)(encoders[0] + encoders[1]))/2d;
    }

    public void usePIDOutput(double output)
    {
        // TODO: tune this
        // If output is less than 10%, do nothing, otherwise, convert to 20%-60%
        if (Math.abs(output) < 0.1)
            output = 0;
        else
            output = output * 0.4 + Math.copySign(0.2, output);
        Robot.chassis.drivetrain.drive(output, 0);
    }
}
