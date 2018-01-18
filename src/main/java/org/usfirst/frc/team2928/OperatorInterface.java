package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
//import org.usfirst.frc.team2928.commands.*;

import java.util.ArrayList;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    // JOYSTICKS AND BUTTONS ARE DEFINED HERE

    public OperatorInterface() {

        // DRIVE STATION IS SET UP HERE
        // EXAMPLE: drivestick = new Joystick(DRIVE_JOYSTICK_PORT);
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }
    public double getDriveX(){
        return -driveStick.getX();
    }
}