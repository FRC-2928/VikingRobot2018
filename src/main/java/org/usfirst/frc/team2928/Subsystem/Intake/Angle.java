package org.usfirst.frc.team2928.Subsystem.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Angle extends Subsystem {
    TalonSRX angleMotor;

    public Angle()
    {
        angleMotor = new WPI_TalonSRX(RobotMap.TALON_INTAKE_ANGLE);
    }

    public void setPower(double power)
    {
        angleMotor.set(ControlMode.PercentOutput, power);
    }

    public void initDefaultCommand()
    {

    }
}
