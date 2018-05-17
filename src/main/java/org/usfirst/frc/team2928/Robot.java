package org.usfirst.frc.team2928;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.team2928.Command.Chassis.DistanceDrive;
import org.usfirst.frc.team2928.Command.Chassis.ResetSensors;
import org.usfirst.frc.team2928.Command.Chassis.Rotate;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Command.CommandGroupBuilder;
import org.usfirst.frc.team2928.Command.Shooter.Calibrate;
import org.usfirst.frc.team2928.Command.Shooter.SetLifter;
import org.usfirst.frc.team2928.Subsystem.Chassis.Chassis;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Shooter.Shooter;

/**
 * Robot for 2018.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Robot extends IterativeRobot {

    private SendableChooser<Field.FieldPosition> startingPositionSelector;
    private Compressor compressor;
    public static Chassis chassis;
    public static Shooter shooter;
    public static OperatorInterface oi;

    @Override
    public void robotInit() {
        compressor = new Compressor();
        chassis = new Chassis();
        shooter = new Shooter();
        compressor.start();
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
        new CommandGroupBuilder()
                .addSequential(new Calibrate())
                .delay(0.1)
                .addSequential(new SetLifter(0.2))
                .build().start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
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