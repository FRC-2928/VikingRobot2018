package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team2928.Command.JoystickDrive;

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

    public Drivebase() {
        left = new WPI_TalonSRX(0);
        leftSlave = new WPI_TalonSRX(1);
        leftSlave.set(ControlMode.Follower, left.getBaseID());

        right = new WPI_TalonSRX(2);
        rightSlave = new WPI_TalonSRX(3);
        rightSlave.set(ControlMode.Follower, right.getBaseID());

        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        drive = new DifferentialDrive(left, right);
    }

    public void drive(double move, double rotate)
    {
        drive.arcadeDrive(move, rotate);
    }
}