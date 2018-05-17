package org.usfirst.frc.team2928.Subsystem.Arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Shoulder extends Subsystem {

    private WPI_TalonSRX motor;

    public Shoulder()
    {
        motor = new WPI_TalonSRX(RobotMap.TALON_4BAR);
        motor.setInverted(true);
    }

    public void setPower(double power) {
        motor.set(ControlMode.PercentOutput, power);
    }
    @Override
    protected void initDefaultCommand() {

    }
}
