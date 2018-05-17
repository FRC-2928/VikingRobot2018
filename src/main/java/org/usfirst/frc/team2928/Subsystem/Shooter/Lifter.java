package org.usfirst.frc.team2928.Subsystem.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.Subsystem.Chassis.SRXGains;
import org.usfirst.frc.team2928.Subsystem.Chassis.VikingSRX;

import static org.usfirst.frc.team2928.RobotConstants.CAN_TIMEOUT_MS;
import static org.usfirst.frc.team2928.RobotMap.TALON_LIFTER;

public class Lifter extends Subsystem {

    VikingSRX lifter;
    private int maxAngle;

    public void initDefaultCommand()
    {
        return;
    }

    public Lifter()
    {
        lifter = new VikingSRX(TALON_LIFTER);
        lifter.setInverted(true);

        lifter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, CAN_TIMEOUT_MS);
        lifter.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, CAN_TIMEOUT_MS);
        lifter.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 10, CAN_TIMEOUT_MS);
        lifter.setGains(new SRXGains(0.5, 0, 0, 0.2));

        lifter.configMotionCruiseVelocity(40000, CAN_TIMEOUT_MS);
        lifter.configMotionAcceleration(15000, CAN_TIMEOUT_MS);
    }

    public void set(double output)
    {
        lifter.set(output);
    }

    public void setPercent(double percent)
    {
        lifter.set(ControlMode.MotionMagic, percent * maxAngle);
    }

    public int getEncoderVelocity()
    {
        return lifter.getEncoderVelocity();
    }
    public void calibrate()
    {
        while(!lifter.getSensorCollection().isRevLimitSwitchClosed())
            lifter.set(-0.75);
        // This is bad. Do not do this.
        // Do as I say, not as I do. -- Sam
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lifter.setSelectedSensorPosition(0, 0, CAN_TIMEOUT_MS);
        while(!lifter.getSensorCollection().isFwdLimitSwitchClosed())
            lifter.set(0.75);
        maxAngle = lifter.getEncoderPosition();
        System.out.println("Maxangle = " + maxAngle);
    }
}
