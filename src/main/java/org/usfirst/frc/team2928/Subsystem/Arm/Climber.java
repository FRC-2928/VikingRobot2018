package org.usfirst.frc.team2928.Subsystem.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Climber extends Subsystem {
    private final Solenoid climber;
    private final WPI_TalonSRX talon;

    private ClimberState lastState = ClimberState.FREE;
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
        talon.setInverted(true);
        setRatchet(ClimberState.FREE);
    }

    private void setRatchet(ClimberState state) {
        // Setting the solenoid to true opens the climber
        if (state == ClimberState.FREE && lastState == ClimberState.RATCHETED)
            talon.set(-1);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        climber.set(state.equals(ClimberState.RATCHETED));

        lastState = state;
    }

    public void runClimber(double power)
    {
        if (power > 0)
            setRatchet(ClimberState.FREE);

        if (power < 0)
            setRatchet(ClimberState.RATCHETED);
        talon.set(power);
    }
}
