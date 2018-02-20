package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Command.ToggleShift;
import org.usfirst.frc.team2928.Command.*;

import java.util.ArrayList;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);
    private static final JoystickButton shift = new JoystickButton(driveStick, 1);
    private static final JoystickButton intakeButton = new JoystickButton(driveStick, 2);
    private static final JoystickButton grabber = new JoystickButton(driveStick, 3);
    private static final JoystickButton toggleIntake = new JoystickButton(driveStick, 4);
    //setup: private static final JoystickButton toggleGrabber = new JoystickButton(operatorConsole, 2);
    //JOYSTICKS AND BUTTONS ARE DEFINED HERE

    public OperatorInterface() {
        shift.whenPressed(new ToggleShift());
        intakeButton.whileHeld(new RunIntake());
        grabber.whenPressed(new ToggleGrabber());
        toggleIntake.whenPressed(new ToggleIntake());
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return driveStick.getX();
    }
}