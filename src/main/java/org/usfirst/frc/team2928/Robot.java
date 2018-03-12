package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Autonomous.DriveForward;
import org.usfirst.frc.team2928.Command.JoystickDrive;
import org.usfirst.frc.team2928.Command.Shift;
import org.usfirst.frc.team2928.Subsystem.*;

import java.util.List;

/**
 * Robot for 2018.
 */
public class Robot extends IterativeRobot {

    //TODO: add to these command groups to make the robot do anything during auto.
    private static SendableChooser<Command> autoSelector;
    private static Compressor compressor = new Compressor();

    public static final Drivebase drivebase = new Drivebase();
    public static final Transmission transmission = new Transmission();
    public static final Shoulder shoulder = new Shoulder();
    public static final Grabber grabber = new Grabber();
    public static final Slider slider = new Slider();
    public static final Intake intake = new Intake();
    public static final IntakeClamp intakeClamp = new IntakeClamp();
    public static final Petemobile petemobile = new Petemobile();
    public static OperatorInterface oi;

    @Override
    public void robotInit() {
        compressor.start();
        autoSelector = new SendableChooser<>();
        autoSelector.addDefault("Drive Forward", new DriveForward(2));
        SmartDashboard.putData("Auto Chooser", autoSelector);
        oi = new OperatorInterface();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        new Shift(Transmission.GearState.HIGH).start();
        new DriveForward(3).start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit()
    {
        NotifierManager.getInstance().stopAllNotifiers();
    }
}