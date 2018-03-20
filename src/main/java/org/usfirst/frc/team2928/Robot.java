package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.Autonomous.Unfold;
import org.usfirst.frc.team2928.Command.Chassis.ResetSensors;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.MotionProfiling.FollowProfile;
import org.usfirst.frc.team2928.Subsystem.Arm.Arm;
import org.usfirst.frc.team2928.Subsystem.Chassis.Chassis;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Intake.Intake;

/**
 * Robot for 2018.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Robot extends IterativeRobot {

    private SendableChooser<Command> autoSelector;
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
        autoSelector.addDefault("Do Nothing", new WaitCommand(0));
        SmartDashboard.putData("Auto Chooser", autoSelector);
        oi = new OperatorInterface();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
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
        new ResetSensors().start();

        new Shift(Transmission.GearState.LOW).start();
        //new FollowProfile("tenFeetTest").start();
        //new Unfold().start();
        new FollowProfile("tenFeetTest").start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();
        chassis.drivetrain.stopProfileDrive();
        new ResetSensors().start();
    }

    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }
}
