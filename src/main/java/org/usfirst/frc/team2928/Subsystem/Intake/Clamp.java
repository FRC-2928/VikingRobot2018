package org.usfirst.frc.team2928.Subsystem.Intake;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Clamp extends Subsystem {

    public enum ClampState {
        OPEN,
        CLOSE;

        public ClampState toggle() {
            return this.equals(CLOSE) ? ClampState.OPEN : ClampState.CLOSE;
        }

    }

    private Solenoid clampIn;
    private Solenoid clampOut;
    private ClampState currentState;

    @Override
    protected void initDefaultCommand() {

    }

    public Clamp() {
        clampIn = new Solenoid(RobotMap.SOLENOID_INTAKE_IN);
        clampOut = new Solenoid(RobotMap.SOLENOID_INTAKE_OUT);

        clampIn.set(true);
        clampOut.set(false);
        currentState = ClampState.CLOSE;
    }

    public void set(ClampState state){
        if (state == ClampState.CLOSE)
        {
            clampIn.set(true);
            clampOut.set(false);
        }
        else
        {
            clampIn.set(false);
            clampOut.set(true);
        }
        currentState = state;
    }

    private ClampState getClampState() {
        return currentState;
    }

    public void toggle() {
        set(getClampState().toggle());
    }

}