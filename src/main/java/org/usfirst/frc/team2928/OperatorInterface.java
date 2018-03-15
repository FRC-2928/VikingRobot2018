package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2928.Command.*;
import org.usfirst.frc.team2928.Subsystem.Transmission;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);

    public static final JoystickButton frontplateUpButton = new JoystickButton(operatorConsole, 7);
    public static final JoystickButton frontplateDownButton = new JoystickButton(operatorConsole, 6);
    public static final JoystickButton fourbarUpButton = new JoystickButton(operatorConsole, 5);
    public static final JoystickButton fourbarDownButton = new JoystickButton(operatorConsole, 4);
    public static final JoystickButton outtakeButton = new JoystickButton(operatorConsole, 10);
    public static final JoystickButton intakeButton = new JoystickButton(operatorConsole, 11);
    public static final JoystickButton gripperButton = new JoystickButton(operatorConsole, 9);
    public static final JoystickButton intakeOpenButton = new JoystickButton(operatorConsole, 2);
    public static final JoystickButton intakeCloseButton = new JoystickButton(operatorConsole, 3);

    public static final JoystickButton gearButton = new JoystickButton(driveStick, 9);


    OperatorInterface() {
        frontplateUpButton.whileHeld(new MoveSlider(RobotConstants.SLIDER_POWER));
        frontplateDownButton.whileHeld(new MoveSlider(-RobotConstants.SLIDER_POWER));
        fourbarUpButton.whileHeld(new DriveShoulder(0.8));
        fourbarDownButton.whileHeld(new DriveShoulder(-0.6));
        outtakeButton.whileHeld(new RunIntake(-0.95));
        intakeButton.whileHeld(new RunIntake(0.6));
        gripperButton.whenPressed(new CloseGrabber());
        gripperButton.whenReleased(new OpenGrabber());
        intakeOpenButton.whenPressed(new OpenIntakeClamp());
        intakeCloseButton.whenPressed(new CloseIntakeClamp());

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