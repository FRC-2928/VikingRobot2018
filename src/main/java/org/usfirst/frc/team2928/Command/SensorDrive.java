package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team2928.Robot;

public class SensorDrive extends PIDCommand {

    private int setpoint;

    public SensorDrive(int distance)
    {
        /***
         * @param distance distance to drive in ticks
         */
        super(0, 0, 0);
        setpoint = distance;
    }
    public void initialize()
    {
        requires(Robot.drivebase);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {

    }
}
