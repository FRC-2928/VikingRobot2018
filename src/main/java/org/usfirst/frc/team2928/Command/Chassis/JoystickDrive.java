package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Robot;

public class JoystickDrive extends Command {

    @Override
    protected boolean isFinished() {
        return false;
    }

    public JoystickDrive()
    {
        requires(Robot.chassis.drivetrain);
    }

    public void initialize()
    {
    }

    public void execute() {
        double driveX = Robot.oi.getDriveX();
        double driveY = Robot.oi.getDriveY();

        Robot.chassis.drivetrain.drive(driveY, driveX);
        int[] encoders = Robot.chassis.drivetrain.getEncoderPositions();
        SmartDashboard.putNumberArray("Encoders", new double[] {encoders[0], encoders[1]});
    }
}