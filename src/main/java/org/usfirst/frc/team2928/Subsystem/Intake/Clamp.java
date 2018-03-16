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

    private Solenoid clamp;

    @Override
    protected void initDefaultCommand() {

    }

    public Clamp() {
        clamp = new Solenoid(RobotMap.SOLENOID_INTAKE);
    }

    public void open(){
        clamp.set(false);
    }

    public void close(){
        clamp.set(true);
    }

    public void set(ClampState state){
        clamp.set(state.equals(ClampState.CLOSE));
    }

    private ClampState getClampState() {
        return clamp.get() ? ClampState.CLOSE : ClampState.OPEN;
    }

    public void toggle() {
        set(getClampState().toggle());
    }

}