package org.usfirst.frc.team2928.Subsystem.Arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Climber extends Subsystem {
    private final Solenoid climber;
    private final WPI_TalonSRX talon;

    private ClimberState lastState;
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
    }

    private void setRatchet(ClimberState state) {
        // Setting the solenoid to true opens the grabber
        climber.set(state.equals(ClimberState.RATCHETED));
    }

    public ClimberState getGrabberState() {
        return climber.get() ? ClimberState.RATCHETED : ClimberState.FREE;
    }

    public void toggle() {
        setRatchet(getGrabberState().toggle());
    }

    public void runClimber(double power)
    {
        talon.set(power);
    }
}
