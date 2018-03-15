package org.usfirst.frc.team2928.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

import static java.lang.System.currentTimeMillis;
import static org.usfirst.frc.team2928.Subsystem.Transmission.GearState.HIGH;
import static org.usfirst.frc.team2928.Subsystem.Transmission.GearState.LOW;

public class Transmission extends Subsystem {

    private final Solenoid shiftSolenoid;

    private long lastShift = 0;

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
        long time = currentTimeMillis();
        if ((time - lastShift) > RobotConstants.SHIFT_DELAY_MS)
        {
            shiftSolenoid.set(state == HIGH);
            lastShift = time;
        }
        SmartDashboard.putString("Gear", state == GearState.LOW ? "Low" : "High");
    }

    public GearState getGear()
    {
        return shiftSolenoid.get() ? HIGH : LOW;
    }

    @Override
    protected void initDefaultCommand() {
    }
}
