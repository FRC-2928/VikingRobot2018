package org.usfirst.frc.team2928.Subsystem.Arm;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Grabber extends Subsystem {
    private final Solenoid grabber;

    public enum GrabberState {
        OPEN,
        CLOSE;

        public GrabberState toggle() {
            return this.equals(CLOSE) ? GrabberState.OPEN : GrabberState.CLOSE;
        }
    }

    @Override
    protected void initDefaultCommand() {
        return;
    }


    public Grabber() {
        grabber = new Solenoid(RobotMap.SOLENOID_GRABBER);
    }

    public void open() {
        grabber.set(true);
    }

    public void close() {
        grabber.set(false);
    }

    public void set(GrabberState state) {
        // Setting the solenoid to true opens the grabber
        grabber.set(state.equals(GrabberState.OPEN));
    }

    public GrabberState getGrabberState() {
        return grabber.get() ? GrabberState.OPEN : GrabberState.CLOSE;
    }

    public void toggle() {
        set(getGrabberState().toggle());
    }

}
