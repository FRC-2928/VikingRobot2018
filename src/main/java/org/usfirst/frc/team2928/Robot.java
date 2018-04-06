package org.usfirst.frc.team2928;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Autonomous.*;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Chassis.DistanceDrive;
import org.usfirst.frc.team2928.Command.Chassis.ResetSensors;
import org.usfirst.frc.team2928.Command.Chassis.Rotate;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Command.OneShotCommand;
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
        autoSelector.addObject("Do Nothing [Works]", Auto.NOTHING);
        autoSelector.addDefault("Switch [Middle works, sides experimental]", Auto.SWITCH);
        autoSelector.addObject("Side of switch [Middle works, sides experimental", Auto.SIDE_SWITCH_HOOK);
        autoSelector.addObject("Cross line [From side][Works]", Auto.LINE);
        autoSelector.addObject("Scale [From side][Experimental]", Auto.SCALE);
        autoSelector.addObject("\"Magic\" scale [From side][Experimental]", Auto.MAGIC_SCALE);
        autoSelector.addObject("Scale/Switch/Line [Middle works, sides experimental]", Auto.SCALE_SWITCH_LINE);
        autoSelector.addObject("Switch/Scale/Line [Middle works, sides experimental]", Auto.SWITCH_SCALE_LINE);
        autoSelector.addObject("Rotate 90 degrees CCW [Test]", Auto.TEST_ROTATION);
        autoSelector.addObject("Drive 5 feet forward [Test]", Auto.TEST_DISTANCE);
        SmartDashboard.putData("Auto Chooser", autoSelector);

        startingPositionSelector = new SendableChooser<>();
        startingPositionSelector.addObject("Left", Field.FieldPosition.LEFT);
        startingPositionSelector.addDefault("Middle", Field.FieldPosition.MIDDLE);
        startingPositionSelector.addObject("Right", Field.FieldPosition.RIGHT);
        SmartDashboard.putData("Starting Position", startingPositionSelector);
        CameraServer.getInstance().startAutomaticCapture();
        oi = new OperatorInterface();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        chassis.drivetrain.resetTalons();
        chassis.drivetrain.setMotorSafetyEnabled(true);
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
        chassis.drivetrain.setMotorSafetyEnabled(false);
        new SetGrabber(Grabber.GrabberState.CLOSE).start();
        while (!Field.getInstance().update()) try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new ResetSensors().start();
        new Shift(Transmission.GearState.LOW).start();

        Auto auto = autoSelector.getSelected();
        Field.FieldPosition startingPosition = startingPositionSelector.getSelected();
        switch (auto) {
            case SWITCH: {
                if (startingPosition == Field.FieldPosition.MIDDLE)
                {
                    new MidSwitchAuto(Field.getInstance().getNearSwitch()).start();
                } else if (startingPosition == Field.getInstance().getNearSwitch())
                {
                    new SideSwitchAuto(Field.getInstance().getNearSwitch(), startingPosition).start();
                } else
                {
                    new CrossLine().start();
                }
                break;
            }
            case LINE: {
                new CrossLine().start();
                break;
            }
            case SCALE: {
                new ScaleAuto(Field.getInstance().getScale(), startingPosition).start();
                break;
            }
            case MAGIC_SCALE: {
                new MagicScaleAuto(Field.getInstance().getScale(), startingPosition).start();
                break;
            }
            case SCALE_SWITCH_LINE:
            {
                if (startingPosition == Field.FieldPosition.MIDDLE)
                {
                    new MidSwitchAuto(Field.getInstance().getNearSwitch()).start();
                } else if (startingPosition == Field.getInstance().getScale())
                {
                    new MagicScaleAuto(Field.getInstance().getScale(), startingPosition).start();
                } else if (startingPosition == Field.getInstance().getNearSwitch())
                {
                    new SideSwitchAuto(Field.getInstance().getNearSwitch(), startingPosition).start();
                } else
                {
                    new CrossLine().start();
                }
                break;
            }
            case SWITCH_SCALE_LINE:
            {
                if (startingPosition == Field.FieldPosition.MIDDLE)
                {
                    new MidSwitchAuto(Field.getInstance().getNearSwitch()).start();
                } else if (startingPosition == Field.getInstance().getNearSwitch())
                {
                    new SideSwitchAuto(Field.getInstance().getNearSwitch(), startingPosition).start();
                } else if (startingPosition == Field.getInstance().getScale())
                {
                    new MagicScaleAuto(Field.getInstance().getScale(), startingPosition).start();
                } else
                {
                    new CrossLine().start();
                }
                break;
            }

            case TEST_ROTATION:
            {
                new Rotate(90).start();
                break;
            }
            case TEST_DISTANCE:
            {
                new DistanceDrive(5).start();
                break;
            }
            case SIDE_SWITCH_HOOK:
            {
                new SideSwitchAuto(Field.getInstance().getNearSwitch(), startingPosition).start();
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
