package org.usfirst.frc.team2928.Command;

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
        requires(Robot.drivebase);
    }
    public void initialize() {
        requires(Robot.drivebase);
    }

    public void execute() {
        double driveX = Robot.oi.getDriveX();
        double driveY = Robot.oi.getDriveY();
        Robot.drivebase.drive(driveY, driveX);
        SmartDashboard.putNumberArray("Joystick Axes", new double[]{driveX, driveY});
    }
}