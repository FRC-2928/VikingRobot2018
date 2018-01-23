package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Command.JoystickDrive;
import org.usfirst.frc.team2928.RobotConstants;
import org.usfirst.frc.team2928.RobotMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Drivebase extends Subsystem {

    private final WPI_TalonSRX left;
    private final WPI_TalonSRX leftSlave;
    private final WPI_TalonSRX right;
    private final WPI_TalonSRX rightSlave;

    private DifferentialDrive drive;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    // TODO: constants for talon ports
    // TODO: Do we need to keep left/rightSlave around?
    public Drivebase() {
        left = new WPI_TalonSRX(RobotMap.TALON_FRONT_LEFT);
        leftSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_RIGHT);
        leftSlave.set(ControlMode.Follower, left.getBaseID());

        right = new WPI_TalonSRX(RobotMap.TALON_FRONT_RIGHT);
        rightSlave = new WPI_TalonSRX(RobotMap.TALON_BACK_RIGHT);
        rightSlave.set(ControlMode.Follower, right.getBaseID());

        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotConstants.TALON_PRIMARY_CLOSED_LOOP, RobotConstants.TALON_TIMEOUT_MS);

        drive = new DifferentialDrive(left, right);
    }
        public void drive(double move, double rotate) {
            drive.arcadeDrive(move, rotate);
            SmartDashboard.putNumber("gyro", getAngle());
        }

        public double getAngle()
        {
            throw new NotImplementedException();
        }
}