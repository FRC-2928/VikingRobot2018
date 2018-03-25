package org.usfirst.frc.team2928.Subsystem.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Climber extends Subsystem {
    private final Solenoid climber;
    private final WPI_TalonSRX talon;

    private double lastPower;
    private int backDriveCounter;
    public static final int BACKDRIVE_CYCLES = 5;
    public enum ClimberState {
        RATCHETED,
        FREE;

        ClimberState toggle() {
            return this.equals(FREE) ? ClimberState.RATCHETED : ClimberState.FREE;
        }
    }

    @Override
    protected void initDefaultCommand() {
    }


    public Climber() {
        talon = new WPI_TalonSRX(RobotMap.TALON_CLIMBER);
        climber = new Solenoid(RobotMap.SOLENOID_CLIMBER);
        setRatchet(ClimberState.RATCHETED);
        lastPower = 0;
        backDriveCounter = BACKDRIVE_CYCLES;
    }

    private void setRatchet(ClimberState state) {
        // Setting the solenoid to true opens the climber
        climber.set(state.equals(ClimberState.FREE));
    }

    public void runClimber(double power)
    {
        if (power < 0) {
            if (lastPower >= 0 && backDriveCounter > 0)
            {
                talon.set(0.4);
                backDriveCounter--;
                if (backDriveCounter == 0)
                    lastPower = power;
            } else
            {
                setRatchet(ClimberState.FREE);
                talon.set(power);
                backDriveCounter = BACKDRIVE_CYCLES;
                lastPower = power;
            }
        }
        if (power > 0) {
            setRatchet(ClimberState.RATCHETED);
            talon.set(power);
            backDriveCounter = BACKDRIVE_CYCLES;
            lastPower = power;
        }
        if (power == 0)
        {
            talon.set(0);
            backDriveCounter = BACKDRIVE_CYCLES;
        }
    }
}
