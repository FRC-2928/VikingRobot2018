package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team2928.Conversions;
import org.usfirst.frc.team2928.Robot;

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
        requires(Robot.drivebase);
        this.ticks = (int)Conversions.FeetToTicks(feet);
    }

    public void initialize()
    {
        Robot.drivebase.zeroSensors();
        setSetpoint(ticks);
    }

    public double returnPIDInput()
    {
        int[] encoders = Robot.drivebase.getEncoders();
        System.out.println(encoders[0] + "\t" + encoders[1]);
        return ((double)(encoders[0] + encoders[1]))/2d;
    }

    public void usePIDOutput(double output)
    {
        Robot.drivebase.drive(0, output);
        System.out.println(output);
    }
}
