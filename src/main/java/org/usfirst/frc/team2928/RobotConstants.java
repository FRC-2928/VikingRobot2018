package org.usfirst.frc.team2928;

public class RobotConstants {

    public static final int CAN_TIMEOUT_MS = 10;
    public static final int PID_SLOT_FORWARD = 0;
    public static final double WHEEL_CIRCUMFERENCE_FEET = 13.976/12.0;
    public static final double MAX_FEET_PER_SECOND = 19.5;

    public static final int DRIVE_TICKS_PER_ROTATION_HIGH = 1530;
    public static final int DRIVE_TICKS_PER_ROTATION_LOW = 2320; // TODO: set this

    public static final int DRIVE_TICKS_PER_FOOT = 2060;
    public static final double TALON_P = 5;
    public static final double TALON_I = 0;
    public static final double TALON_D = 0.1;
    public static final double TALON_F = .616; // .616 is calculated kF
    public static final double PROFILE_TICK_MS = 50;

    public static final double AXLE_LENGTH_FEET = 1.875;

    public static final long SHIFT_DELAY_MS = 100;

    public static final double SLIDER_POWER = 0.8;
}