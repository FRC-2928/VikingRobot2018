package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Intake extends Subsystem {

    private Solenoid gripper;
    private WPI_TalonSRX angleMotor;
    // Do we have 2 intakes or 1?
    private WPI_TalonSRX intakeLeft;
    private WPI_TalonSRX intakeRight;
    @Override
    protected void initDefaultCommand() {

    }

    public Intake()
    {
        gripper = new Solenoid(RobotMap.SOLENOID_INTAKE);
        angleMotor = new WPI_TalonSRX(RobotMap.TALON_INTAKE_ANGLE);
        intakeLeft = new WPI_TalonSRX(RobotMap.TALON_INTAKE_LEFT);
        intakeRight = new WPI_TalonSRX(RobotMap.TALON_INTAKE_RIGHT);
    }
}
