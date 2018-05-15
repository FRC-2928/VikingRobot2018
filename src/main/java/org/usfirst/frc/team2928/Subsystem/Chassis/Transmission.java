package org.usfirst.frc.team2928.Subsystem.Chassis;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

import static java.lang.System.currentTimeMillis;
import static org.usfirst.frc.team2928.Subsystem.Chassis.Transmission.GearState.HIGH;
import static org.usfirst.frc.team2928.Subsystem.Chassis.Transmission.GearState.LOW;

public class Transmission extends Subsystem {

    private final Solenoid shiftSolenoid;
    private GearState currentState;

    private long lastShift = 0;

    public enum GearState
    {
        HIGH(false),
        LOW(true);

        private boolean booleanValue;
        GearState(boolean booleanValue)
        {
            this.booleanValue = booleanValue;
        }

        public GearState toggle()
        {
            return this.equals(HIGH) ? LOW : HIGH;
        }
        public boolean toBoolean()
        {
            return this.booleanValue;
        }
    }

    public Transmission()
    {
        shiftSolenoid = new Solenoid(RobotMap.SOLENOID_TRANSMISSION);
        shiftSolenoid.set(LOW.toBoolean());
        currentState = LOW;
    }

    public void shift(GearState state)
    {
        long time = currentTimeMillis();
        if ((time - lastShift) > RobotConstants.SHIFT_DELAY_MS)
        {
            System.out.println("Shifting");
            shiftSolenoid.set(state.toBoolean());
            currentState = state;
            lastShift = time;
        }
        SmartDashboard.putString("Gear", state == GearState.HIGH ? "Low" : "High");
    }

    public void toggle()
    {
        shift(getGear().toggle());
    }

    public GearState getGear()
    {
        return currentState;
    }

    @Override
    protected void initDefaultCommand() {
    }
}
