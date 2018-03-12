package org.usfirst.frc.team2928;

public class RobotConstants {

    public static final int TALON_TIMEOUT_MS = 10;
    public static final int TALON_PRIMARY_CLOSED_LOOP = 0;
    public static final double WHEEL_CIRCUMFERENCE_FEET = 14.0/12.0;
    public static final double MAX_FEET_PER_SECOND = 19.5;
    public static final double NULL_ZONE_RANGE = 90d;

    public static final int DRIVE_TICKS_PER_ROTATION = 1530;
    public static final double DRIVE_POSITION_P = 0.2;
    public static final double DRIVE_POSITION_I = 0;
    public static final double DRIVE_POSITION_D = 0;
    public static final double DRIVE_POSITION_F = 0.2;
    public static final double DRIVE_VELOCITY_P = 0.2;
    public static final double DRIVE_VELOCITY_I = 0;
    public static final double DRIVE_VELOCITY_D = 0;
    public static final double DRIVE_VELOCITY_F = 0.2;
    public static final int DRIVE_PID_VELOCITY_SLOT = 1;
    public static final int DRIVE_PID_POSITION_SLOT = 0;

    public static final double AXLE_LENGTH_FEET = 1.875;

    public static final double PATHFINDER_P = 0.02;
    public static final double PATHFINDER_I = 0.0;
    public static final double PATHFINDER_D = 0;
    public static final double PATHFINDER_ACCEL = 1;
    public static final double PATHFINDER_VELOCTIY = 4;
    public static final double PATHFINDER_TIME_INTERVAL = 0.05;

    public static final long SHIFT_DELAY_MS = 100;

    public static final double SLIDER_POWER = 0.8;
}