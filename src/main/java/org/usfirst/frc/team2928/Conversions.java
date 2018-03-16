package org.usfirst.frc.team2928;

import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;

public class Conversions {
    public static double FeetToTicks(double feet, Transmission.GearState gear)
    {
        return feet * (gear == Transmission.GearState.HIGH ? RobotConstants.DRIVE_TICKS_PER_ROTATION_HIGH : RobotConstants.DRIVE_TICKS_PER_ROTATION_LOW) / RobotConstants.WHEEL_CIRCUMFERENCE_FEET;
    }

    public static double TicksToFeet(double ticks, Transmission.GearState gear)
    {
        return ticks * RobotConstants.WHEEL_CIRCUMFERENCE_FEET / (gear == Transmission.GearState.HIGH ? RobotConstants.DRIVE_TICKS_PER_ROTATION_HIGH : RobotConstants.DRIVE_TICKS_PER_ROTATION_LOW);
    }

    public static double FeetToMeters(double feet)
    {
        return 0.3048 * feet;
    }

    public static double MetersToFeet(double meters)
    {
        return meters / 0.3048;
    }
}
