package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Robot;

public class JoystickDrive extends Command {

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void initialize() {
        requires(Robot.drivebase);
    }

    public void execute() {
        Robot.drivebase.drive(Robot.oi.getDriveX(), Robot.oi.getDriveY());
    }
}