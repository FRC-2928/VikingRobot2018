package org.usfirst.frc.team2928.Subsystem.Chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Command.Chassis.JoystickDrive;
import org.usfirst.frc.team2928.MotionProfiling.Profile;
import org.usfirst.frc.team2928.MotionProfiling.ProfileFollower;
import org.usfirst.frc.team2928.Robot;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;

public class Drivetrain extends Subsystem {

    public final VikingSRX left;
    private final VikingSRX leftFollower;
    public final VikingSRX right;
    private final VikingSRX rightFollower;

    private PigeonIMU pigeon;
    private DifferentialDrive drive;

    public ProfileFollower profileFollower;
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public Drivetrain()
    {
        // Initialize talons
        left = new VikingSRX(RobotMap.TALON_FRONT_LEFT);
        leftFollower = new VikingSRX(RobotMap.TALON_BACK_LEFT);
        leftFollower.set(ControlMode.Follower, RobotMap.TALON_FRONT_LEFT);

        right = new VikingSRX(RobotMap.TALON_FRONT_RIGHT);
        rightFollower = new VikingSRX(RobotMap.TALON_BACK_RIGHT);
        rightFollower.set(ControlMode.Follower, RobotMap.TALON_FRONT_RIGHT);

        // Invert the right side of the drivetrain, so both sides go the same way
        right.setInverted(true);
        rightFollower.setInverted(true);

        for (VikingSRX v : new VikingSRX[] {left, right}) {
            v.setGains(new SRXGains(RobotConstants.TALON_P, RobotConstants.TALON_I, RobotConstants.TALON_D, RobotConstants.TALON_F));
            // We use quad encoders on this years robot
            v.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotConstants.CAN_TIMEOUT_MS);
            // Invert the encoder readings so a forward move on the robot is a positive change in the encoder reading
            v.setSensorPhase(true);
            // Messages are send periodically to avoid spamming the can bus with requests, set the rate to match how often we need it
            v.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, (int)RobotConstants.PROFILE_TICK_MS, RobotConstants.CAN_TIMEOUT_MS);
            // Default is 4% deadband, we want less.
            v.configNeutralDeadband(0.01, RobotConstants.CAN_TIMEOUT_MS);
            // Our profiles have their own period set
            v.configMotionProfileTrajectoryPeriod(0, RobotConstants.CAN_TIMEOUT_MS);
            // Make it harder for Jett to tip it over, rapid acceleration is BAD with a tall robot.
            v.configOpenloopRamp(0.3, RobotConstants.CAN_TIMEOUT_MS);
            // Set our motion magic velocity and acceleration
            v.configMotionCruiseVelocity(RobotConstants.TALON_CRUISE_VELOCITY, RobotConstants.CAN_TIMEOUT_MS);
            v.configMotionAcceleration(RobotConstants.TALON_MAX_ACCELERATION, RobotConstants.CAN_TIMEOUT_MS);
            // These are the default values but we're supposed to set them anyways.
            v.configNominalOutputForward(0, RobotConstants.CAN_TIMEOUT_MS);
            v.configNominalOutputReverse(0, RobotConstants.CAN_TIMEOUT_MS);
            v.configPeakOutputForward(1, RobotConstants.CAN_TIMEOUT_MS);
            v.configPeakOutputReverse(-1, RobotConstants.CAN_TIMEOUT_MS);
        }

        drive = new DifferentialDrive(left, right);
        drive.setSafetyEnabled(false);

        pigeon = new PigeonIMU(RobotMap.PIGEON);
        setBrakeMode(false);

        profileFollower = new ProfileFollower(left, right);
        setMotorSafetyEnabled(true);

        zeroSensors();
    }

    public void drive(double move, double rotate) {
        this.drive(move, rotate, true);
    }

    public void drive(double move, double rotate, boolean squaredInputs)
    {
        drive.arcadeDrive(rotate, move, squaredInputs); // WPILIB is still backwards
        SmartDashboard.putNumber("gyro", getYaw());
        SmartDashboard.putNumberArray("Encoder Velocity", getEncoderVelocities());
        SmartDashboard.putNumberArray("Encoder Position", getEncoderPositions());
        if (getEncoderVelocities()[0] > SmartDashboard.getNumber("MaxVel", 0))
            SmartDashboard.putNumber("MaxVel", getEncoderVelocities()[0]);
        SmartDashboard.putNumber("Move", move);
        SmartDashboard.putNumber("EncLeft", getEncoderPositions()[0]);
    }

    public double getYaw() {
        double[] angles = {0, 0, 0};
        pigeon.getYawPitchRoll(angles);
        return angles[0];
    }

    public void zeroSensors() {
        zeroEncoders();
        zeroGyro();
    }

    public void zeroGyro()
    {
        pigeon.setYaw(0, RobotConstants.CAN_TIMEOUT_MS);
    }

    public void zeroEncoders()
    {
        left.zeroEncoder();
        right.zeroEncoder();
    }

    public double[] getEncoderPositions() {
        return new double[]{left.getEncoderPosition(), right.getEncoderPosition()};
    }

    public double[] getEncoderVelocities()
    {
        return new double[]{left.getEncoderVelocity(), right.getEncoderVelocity()};
    }

    public void setBrakeMode(boolean brake) {
        left.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
    }

    public boolean doneWithProfile()
    {
        return profileFollower.doneWithProfile();
    }

    public void resetTalons()
    {
        left.reset();
        right.reset();
    }

    public void startProfileDrive()
    {
        profileFollower.startFollowing();
    }

    public void stopProfileDrive()
    {
        profileFollower.stopFollowing();
    }

    public void setProfiles(Profile[] profiles)
    {
        profileFollower.setProfiles(profiles);
    }

    public void setMotorSafetyEnabled(boolean safety)
    {
        drive.setSafetyEnabled(safety);
    }

    public boolean getMotorSafetyEnabled()
    {
        return drive.isSafetyEnabled();
    }

    public void setTalons(ControlMode mode, double value)
    {
        left.set(mode, value);
        right.set(mode, value);
    }

    public void setTalons(ControlMode mode, double leftValue, double rightValue)
    {
        left.set(mode, leftValue);
        right.set(mode, rightValue);
    }

    public void setTalons(double value)
    {
        left.set(value);
        right.set(value);
    }

    public void setTalons(double leftValue, double rightValue)
    {
        left.set(leftValue);
        right.set(rightValue);
    }
}