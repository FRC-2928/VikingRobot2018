package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2928.Command.ToggleShift;
//import org.usfirst.frc.team2928.commands.*;

import java.util.ArrayList;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);
    private static final JoystickButton shift = new JoystickButton(operatorConsole, 1);
    //setup: private static final JoystickButton toggleGrabber = new JoystickButton(operatorConsole, 2);
    //JOYSTICKS AND BUTTONS ARE DEFINED HERE

    public OperatorInterface() {
        shift.whenPressed(new ToggleShift());
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return -driveStick.getZ();
    }
}