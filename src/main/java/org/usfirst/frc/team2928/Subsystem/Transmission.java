package org.usfirst.frc.team2928.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Transmission extends Subsystem {

    private final Solenoid shiftSolenoid;

    public enum GearState
    {
        HIGH,
        LOW;

        public GearState toggle()
        {
            return this.equals(HIGH) ? LOW : HIGH;
        }
    }

    public Transmission()
    {
        shiftSolenoid = new Solenoid(RobotMap.SOLENOID_TRANSMISSION);
    }

    public void shift(GearState state)
    {
        shiftSolenoid.set(state == GearState.LOW);
    }

    public GearState getGear()
    {
        return shiftSolenoid.get() ? GearState.HIGH : GearState.LOW;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
