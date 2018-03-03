package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2928.Command.ToggleShift;
import org.usfirst.frc.team2928.Command.*;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);
    private static final JoystickButton shift = new JoystickButton(driveStick, 1);
    private static final JoystickButton intakeButton = new JoystickButton(driveStick, 2);
    private static final JoystickButton grabber = new JoystickButton(driveStick, 3);
    private static final JoystickButton toggleIntake = new JoystickButton(driveStick, 4);
    private static final JoystickButton moveSliderUp = new JoystickButton(driveStick, 5);
    private static final JoystickButton moveSliderDown = new JoystickButton(driveStick, 6);
    private static final JoystickButton outakeButton = new JoystickButton(driveStick, 7);
    //For moving arm: private static final JoystickButton armToggle = new JoystickButton(driveStick, 8);

    //JOYSTICKS AND BUTTONS ARE DEFINED HERE

    OperatorInterface() {
        shift.whenPressed(new ToggleShift());
        intakeButton.whileHeld(new RunIntake(0.3));
        grabber.whenPressed(new ToggleGrabber());
        toggleIntake.whenPressed(new ToggleIntakeClamp());
        moveSliderUp.whileHeld(new MoveSlider(RobotConstants.SLIDER_POWER));
        moveSliderDown.whileHeld(new MoveSlider(-RobotConstants.SLIDER_POWER));
        outakeButton.whileHeld(new RunIntake(-0.8));
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return driveStick.getX();
    }
}