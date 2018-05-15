package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Command.Shooter.RunIntake;
import org.usfirst.frc.team2928.Command.Shooter.RunLifter;
import org.usfirst.frc.team2928.Command.Shooter.SetTomahawk;
import org.usfirst.frc.team2928.Command.Shooter.Shoot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Shooter.Tomahawk;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);

    public static JoystickButton TomahawkUp = new JoystickButton(operatorConsole, 7);
    public static JoystickButton TomahawkDown = new JoystickButton(operatorConsole, 6);
    public static JoystickButton Flipper = new JoystickButton(operatorConsole, 8);
    public static JoystickButton ShooterUp = new JoystickButton(operatorConsole, 10);
    public static JoystickButton ShooterDown = new JoystickButton(operatorConsole, 11);
    public static JoystickButton IntakeIn = new JoystickButton(operatorConsole, 3);
    public static JoystickButton IntakeOut = new JoystickButton(operatorConsole, 2);
    public static JoystickButton Shifter = new JoystickButton(driveStick, 9);

    OperatorInterface() {
        TomahawkUp.whenPressed(new SetTomahawk(Tomahawk.TomahawkState.RAISE));
        TomahawkDown.whenPressed(new SetTomahawk(Tomahawk.TomahawkState.LOWER));
        Flipper.whileHeld(new Shoot());
        ShooterUp.whileHeld(new RunLifter(0.35));
        ShooterDown.whileHeld(new RunLifter(-0.35));
        IntakeIn.whileHeld(new RunIntake(-0.75));
        IntakeOut.whileHeld(new RunIntake(0.75));
        Shifter.whenPressed(new Shift(Transmission.GearState.HIGH));
        Shifter.whenReleased(new Shift(Transmission.GearState.LOW));
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return driveStick.getX();
    }
}