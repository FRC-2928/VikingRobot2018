package org.usfirst.frc.team2928.Subsystem.Intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Motors extends Subsystem {

    // 2 intake motors
    private Talon intakeLeft;

    @Override
    protected void initDefaultCommand() {

    }

    public Motors() {
        intakeLeft = new Talon(RobotMap.TALON_INTAKE_LEFT);
        intakeLeft.setInverted(true);
    }

    public void set(double power)
    {
        intakeLeft.set(power);
    }
}