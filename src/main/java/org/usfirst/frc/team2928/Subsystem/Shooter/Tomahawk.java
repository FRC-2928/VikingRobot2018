package org.usfirst.frc.team2928.Subsystem.Shooter;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Tomahawk extends Subsystem {
    private Solenoid solenoid;
    public enum TomahawkState
    {
        RAISE(false),
        LOWER(true);

        private boolean booleanValue;
        TomahawkState(boolean booleanValue)
        {
            this.booleanValue = booleanValue;
        }
        public boolean toBoolean()
        {
            return booleanValue;
        }
    }

    public void initDefaultCommand()
    {
        return;
    }

    public Tomahawk()
    {
        solenoid = new Solenoid(RobotMap.SOLENOID_TOMAHAWK);
    }

    public void set(TomahawkState state)
    {
        solenoid.set(state.toBoolean());
    }

    public TomahawkState get()
    {
        return solenoid.get() ? TomahawkState.LOWER : TomahawkState.RAISE;
    }
}
