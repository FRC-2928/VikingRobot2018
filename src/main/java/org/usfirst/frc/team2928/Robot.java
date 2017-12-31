package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2928.autonomous.*;
import org.usfirst.frc.team2928.commands.ConstantDrive;
import org.usfirst.frc.team2928.commands.ShiftDown;
import org.usfirst.frc.team2928.subsystems.*;

/**
 * Robot for 2017.
 */
public class Robot extends IterativeRobot {

    // SET UP SUBSYSTEMS HERE

    // SET UP VISION TRACKING AND OPERATOR INTERFACE HERE

    //TODO: add to these command groups to make the robot do anything during auto.
    private static SendableChooser<Command> autoSelector;
    private static Compressor compressor = new Compressor();

    @Override
    public void robotInit() {
        compressor.start();
        CameraServer.getInstance().startAutomaticCapture(0);
        autoSelector = new SendableChooser<>();
        // SET UP AUTONOMOUS CHOICES HERE

        // VISION TRACKING SETUP GOES HERE

        // OPERATOR INTERFACE GOES HERE
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
        // Attempt to prevent half the talons from cutting out
        autoSelector.getSelected().start();
    }

    @Override
    public void autonomousPeriodic() {
    }
}
