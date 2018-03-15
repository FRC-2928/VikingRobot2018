package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Robot;

import static org.usfirst.frc.team2928.Robot.drivebase;

public class JoystickDrive extends Command {

    @Override
    protected boolean isFinished() {
        return false;
    }

    public JoystickDrive()
    {
        requires(drivebase);
    }

    public void initialize()
    {
    }

    public void execute() {
        double driveX = Robot.oi.getDriveX();
        double driveY = Robot.oi.getDriveY();

        drivebase.drive(driveX, driveY);
        int[] encoders = Robot.drivebase.getEncoders();
        SmartDashboard.putNumberArray("Encoders", new double[] {encoders[0], encoders[1]});
        System.out.println(encoders[0] + "\t" + encoders[1]);
    }
}