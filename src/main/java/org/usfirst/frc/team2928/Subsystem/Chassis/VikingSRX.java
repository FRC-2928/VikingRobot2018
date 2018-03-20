package org.usfirst.frc.team2928.Subsystem.Chassis;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team2928.MotionProfiling.Profile;
import org.usfirst.frc.team2928.RobotConstants;

public class VikingSRX extends WPI_TalonSRX {

    public Profile profile;
    private int pointsSent;
    public VikingSRX(int port)
    {
        super(port);
        reset();
        setGains(new SRXGains(0,0,0,0));
        pointsSent = 0;
    }

    public void zeroEncoder()
    {
        setSelectedSensorPosition(0,0, RobotConstants.CAN_TIMEOUT_MS);
    }

    public void setGains(SRXGains gains)
    {
        config_kP(0, gains.p, RobotConstants.CAN_TIMEOUT_MS);
        config_kI(0, gains.i, RobotConstants.CAN_TIMEOUT_MS);
        config_kD(0, gains.d, RobotConstants.CAN_TIMEOUT_MS);
        config_kF(0, gains.f, RobotConstants.CAN_TIMEOUT_MS);
    }

    public boolean sendNextPoint()
    {
        if (profile == null)
        {
            System.err.println("Attempted to load null profile to talon.");
            return false;
        }
        if (pointsSent >= profile.size())
        {
            System.err.println("Attempted to send more points than were in the profile.");
            return false;
        }

        if (pushMotionProfileTrajectory(profile.getPoint(pointsSent)) == ErrorCode.OK)
        {
            pointsSent++;
            return true;
        }
        return false;
    }

    public MotionProfileStatus getStatus()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        getMotionProfileStatus(status);
        return status;
    }

    public void followInit()
    {
        set(ControlMode.PercentOutput, 0);
        pointsSent = 0;
        zeroEncoder();
        clearMotionProfileTrajectories();
    }

    public void reset()
    {
        set(ControlMode.PercentOutput, 0);
        clearMotionProfileTrajectories();
        zeroEncoder();
        pointsSent = 0;
        profile = null;
    }

    public int getEncoderPosition()
    {
        return getSelectedSensorPosition(0);
    }

    public int getEncoderVelocity()
    {
        return getSelectedSensorVelocity(0);
    }
}
