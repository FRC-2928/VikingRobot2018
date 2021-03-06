package org.usfirst.frc.team2928.Subsystem.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Slider extends Subsystem
{
    private WPI_TalonSRX talon;
    @Override
    public void initDefaultCommand() {}

    public Slider()
    {
        talon = new WPI_TalonSRX(RobotMap.TALON_GRABBER_SLIDER);
        talon.setInverted(false);
    }

    public void setPower(double power)
    {
        talon.set(power);
    }
}
