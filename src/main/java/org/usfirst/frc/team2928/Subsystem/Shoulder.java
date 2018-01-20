package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

import static com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative;

public class Shoulder extends Subsystem {

    // TODO: Tune these
    private static final int CRUISE_VELOCITY = 15000;
    private static final int MAX_ACCELERATION = 6000;

    // TODO: Find out how far we need to turn for a max extension
    private static final int MAX_EXTENSION = 12000;

    private WPI_TalonSRX motor;
    // Static fields for talon closed-loop constants
    public Shoulder()
    {
        motor = new WPI_TalonSRX(RobotMap.TALON_SHOULDER);
        // pidIDX 0 means primary closed-loop
        motor.configSelectedFeedbackSensor(CTRE_MagEncoder_Relative, RobotConstants.TALON_TIMEOUT_MS, RobotConstants.TALON_TIMEOUT_MS);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotConstants.TALON_TIMEOUT_MS);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotConstants.TALON_TIMEOUT_MS);

        motor.configNominalOutputForward(0, RobotConstants.TALON_TIMEOUT_MS);
        motor.configNominalOutputReverse(0, RobotConstants.TALON_TIMEOUT_MS);
        motor.configPeakOutputForward(1, RobotConstants.TALON_TIMEOUT_MS);
        motor.configPeakOutputReverse(-1, RobotConstants.TALON_TIMEOUT_MS);

        motor.configMotionCruiseVelocity(CRUISE_VELOCITY, RobotConstants.TALON_TIMEOUT_MS);
        motor.configMotionAcceleration(MAX_ACCELERATION, RobotConstants.TALON_TIMEOUT_MS);
        motor.setSelectedSensorPosition(0, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);

        motor.config_kP(0, 0.5, RobotConstants.TALON_TIMEOUT_MS);
        motor.config_kI(0, 0.2, RobotConstants.TALON_TIMEOUT_MS);
        motor.config_kD(0, 0.0, RobotConstants.TALON_TIMEOUT_MS);
        // We shouldn't need to ever use this one, setting it to 0 just to be safe
        motor.config_kF(0, 0.0, RobotConstants.TALON_TIMEOUT_MS);
    }

    public void setExtension(double target)
    {
        motor.set(ControlMode.MotionMagic, MAX_EXTENSION * target);
    }

    public double getTargetPercentage()
    {
        return (double)motor.getClosedLoopTarget(RobotConstants.TALON_PRIMARY_CLOSED_LOOP) / MAX_EXTENSION;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
