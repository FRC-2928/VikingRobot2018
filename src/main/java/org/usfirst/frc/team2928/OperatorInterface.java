package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2928.Command.Arm.RunSlider;
import org.usfirst.frc.team2928.Command.Arm.RunShoulder;
import org.usfirst.frc.team2928.Command.Arm.SetGrabber;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Command.Intake.RunMotors;
import org.usfirst.frc.team2928.Command.Intake.SetClamp;
import org.usfirst.frc.team2928.Subsystem.Arm.Grabber;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Intake.Clamp;

/*
Naming conventions are different in this file. This is so that drivers can make changes here if need be; their names for
things don't match up with ours.
frontplate = Arm.Slider
fourbar = Arm.Shoulder
intake/outtake = Intake.Motors
Gripper = Arm.Grabber
intake open/close = Intake.Clamp
 */
public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);

    private static final JoystickButton frontplateUpButton = new JoystickButton(operatorConsole, 7);
    private static final JoystickButton frontplateDownButton = new JoystickButton(operatorConsole, 6);
    private static final JoystickButton fourbarUpButton = new JoystickButton(operatorConsole, 5);
    private static final JoystickButton fourbarDownButton = new JoystickButton(operatorConsole, 4);
    private static final JoystickButton outtakeButton = new JoystickButton(operatorConsole, 10);
    private static final JoystickButton intakeButton = new JoystickButton(operatorConsole, 11);
    private static final JoystickButton gripperButton = new JoystickButton(operatorConsole, 9);
    private static final JoystickButton intakeOpenButton = new JoystickButton(operatorConsole, 2);
    private static final JoystickButton intakeCloseButton = new JoystickButton(operatorConsole, 3);

    private static final JoystickButton gearButton = new JoystickButton(driveStick, 9);

    OperatorInterface() {
        frontplateUpButton.whileHeld(new RunSlider(RobotConstants.SLIDER_POWER));
        frontplateDownButton.whileHeld(new RunSlider(-RobotConstants.SLIDER_POWER));
        fourbarUpButton.whileHeld(new RunShoulder(0.8));
        fourbarDownButton.whileHeld(new RunShoulder(-0.6));
        outtakeButton.whileHeld(new RunMotors(-0.95));
        intakeButton.whileHeld(new RunMotors(0.6));
        gripperButton.whenPressed(new SetGrabber(Grabber.GrabberState.CLOSE));
        gripperButton.whenReleased(new SetGrabber(Grabber.GrabberState.OPEN));
        intakeOpenButton.whenPressed(new SetClamp(Clamp.ClampState.OPEN));
        intakeCloseButton.whenPressed(new SetClamp(Clamp.ClampState.CLOSE));

        gearButton.whenPressed(new Shift(Transmission.GearState.LOW));
        gearButton.whenReleased(new Shift(Transmission.GearState.HIGH));
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return driveStick.getX();
    }
}