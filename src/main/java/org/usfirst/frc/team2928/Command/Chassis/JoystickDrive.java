package org.usfirst.frc.team2928.Command.Chassis;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Robot;

public class JoystickDrive extends Command {

    @Override
    protected boolean isFinished() {
        return !DriverStation.getInstance().isOperatorControl();
    }

    public JoystickDrive()
    {
        requires(Robot.chassis.drivetrain);
    }

    public void initialize()
    {
        Robot.chassis.drivetrain.setMotorSafetyEnabled(true);
    }

    public void execute() {
        double driveX = Robot.oi.getDriveX();
        double driveY = Robot.oi.getDriveY();

        System.out.println("driving");
        Robot.chassis.drivetrain.drive(driveY, driveX);
    }
}