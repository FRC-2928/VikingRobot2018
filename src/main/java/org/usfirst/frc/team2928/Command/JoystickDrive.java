package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Robot;

public class JoystickDrive extends Command {

    private double lastX;
    private double lastY;
    @Override
    protected boolean isFinished() {
        return false;
    }

    public JoystickDrive()
    {
        requires(Robot.drivebase);
    }

    public void initialize()
    {
        lastX = 0;
        lastY = 0;
    }

    public void execute() {
        double driveX = Robot.oi.getDriveX();
        double driveY = Robot.oi.getDriveY();

        driveX = Math.abs(driveX - lastX) < 0.2 ? driveX : 0.2 * driveX;
        driveY = Math.abs(driveY - lastY) < 0.3 ? driveY : 0.3 * driveY;

        Robot.drivebase.drive(driveY, driveX);
        SmartDashboard.putNumberArray("Joystick Axes", new double[]{driveX, driveY});
        Robot.transmission.getGear();

        int[] encoders = Robot.drivebase.getEncoders();
        SmartDashboard.putNumber("Left encoder", encoders[0]);
        SmartDashboard.putNumber("Right encoder", encoders[1]);

        lastX = driveX;
        lastY = driveY;
    }
}