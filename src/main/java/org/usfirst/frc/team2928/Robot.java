package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Autonomous.*;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Chassis.ResetSensors;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Subsystem.Arm.Arm;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;
import org.usfirst.frc.team2928.Subsystem.Chassis.Chassis;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Intake.Intake;

/**
 * Robot for 2018.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Robot extends IterativeRobot {

    private SendableChooser<Auto> autoSelector;
    private SendableChooser<Field.FieldPosition> startingPositionSelector;
    private Compressor compressor;
    public static Arm arm;
    public static Chassis chassis;
    public static Intake intake;
    public static OperatorInterface oi;

    @Override
    public void robotInit() {
        compressor = new Compressor();
        arm = new Arm();
        chassis = new Chassis();
        intake = new Intake();

        compressor.start();
        autoSelector = new SendableChooser<>();
        autoSelector.addDefault("Do Nothing", Auto.NOTHING);
        autoSelector.addObject("Switch from center", Auto.MID_SWITCH);
        autoSelector.addObject("Switch and line from center", Auto.MID_SWITCH_LINE);
        autoSelector.addObject("Switch from side (set starting position!)", Auto.SIDE_SWITCH);
        autoSelector.addObject("Side of switch from side (set starting position)", Auto.SIDE_SWITCH_HOOK);
        autoSelector.addObject("Line from side", Auto.LINE);
        SmartDashboard.putData("Auto Chooser", autoSelector);

        startingPositionSelector = new SendableChooser<>();
        startingPositionSelector.addDefault("Left", Field.FieldPosition.LEFT);
        startingPositionSelector.addObject("Middle", Field.FieldPosition.MIDDLE);
        startingPositionSelector.addObject("Right", Field.FieldPosition.RIGHT);
        SmartDashboard.putData("Starting Position", startingPositionSelector);
        CameraServer.getInstance().startAutomaticCapture();
        oi = new OperatorInterface();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        new Shift(Transmission.GearState.LOW).start();
        chassis.drivetrain.stopProfileDrive();

        new ResetSensors().start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        new SetGrabber(Grabber.GrabberState.CLOSE).start();
        while (!Field.getInstance().update()) try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new ResetSensors().start();
        new Shift(Transmission.GearState.LOW).start();

        Auto auto = autoSelector.getSelected();
        switch (auto) {
            case MID_SWITCH: {
                new MidSwitchAuto(Field.getInstance().getNearSwitch(), false).start();
                break;
            }
            case MID_SWITCH_LINE: {
                new MidSwitchAuto(Field.getInstance().getNearSwitch(), true).start();
                break;
            }
            case SIDE_SWITCH: {
                new SideSwitchAuto(Field.getInstance().getNearSwitch(), startingPositionSelector.getSelected()).start();
                break;
            }
            case SIDE_SWITCH_HOOK: {
                new SideSwitchHookAuto(Field.getInstance().getNearSwitch(), startingPositionSelector.getSelected()).start();
                break;
            }
            case LINE: {
                new CrossLine().start();
                break;
            }
            case NOTHING:
            default: {
                new Unfold().start();
            }
        }


    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        chassis.drivetrain.stopProfileDrive();
        new ResetSensors().start();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }
}
