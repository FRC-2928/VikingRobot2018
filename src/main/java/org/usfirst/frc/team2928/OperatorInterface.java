package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2928.Command.Chassis.Shift;
import org.usfirst.frc.team2928.Command.Shooter.RunIntake;
import org.usfirst.frc.team2928.Command.Shooter.SetLifter;
import org.usfirst.frc.team2928.Command.Shooter.SetTomahawk;
import org.usfirst.frc.team2928.Command.Shooter.Shoot;
import org.usfirst.frc.team2928.Subsystem.Chassis.Transmission;
import org.usfirst.frc.team2928.Subsystem.Shooter.Tomahawk;

public class OperatorInterface {

    private static final Joystick driveStick = new Joystick(0);
    private static final Joystick operatorConsole = new Joystick(1);

    public static final JoystickButton shoot = new JoystickButton(operatorConsole, 8);
    public static final JoystickButton intakeIn = new JoystickButton(operatorConsole, 1);
    public static final JoystickButton intakeOut = new JoystickButton(driveStick, 2);
    public static final JoystickButton shooterDown = new JoystickButton(operatorConsole, 4);
    public static final JoystickButton shooterDrive = new JoystickButton(operatorConsole, 5);
    public static final JoystickButton shooterLow = new JoystickButton(operatorConsole, 6);
    public static final JoystickButton shooterHigh = new JoystickButton(operatorConsole, 7);
    public static final JoystickButton shift = new JoystickButton(driveStick, 9);
    public static final JoystickButton tomahawkUp = new JoystickButton(operatorConsole, 10);
    public static final JoystickButton tomahawkDown = new JoystickButton(operatorConsole, 11);

    OperatorInterface() {
        shoot.whileHeld(new Shoot());
        intakeIn.whileHeld(new RunIntake(-0.75, -0.75));
        intakeOut.whileHeld(new RunIntake(0.75, 0));
        shooterDown.whenPressed(new SetLifter(0));
        shooterDrive.whenPressed(new SetLifter(0.2));
        shooterLow.whenPressed(new SetLifter(0.5));
        shooterHigh.whenPressed(new SetLifter(0.8));
        shift.whenPressed(new Shift(Transmission.GearState.HIGH));
        shift.whenReleased(new Shift(Transmission.GearState.LOW));
        tomahawkDown.whenPressed(new SetTomahawk(Tomahawk.TomahawkState.LOWER));
        tomahawkUp.whenPressed(new SetTomahawk(Tomahawk.TomahawkState.RAISE));
    }

    //We're assuming same drive setup as last year.
    public double getDriveY() {
        return -driveStick.getY();
    }

    public double getDriveX() {
        return driveStick.getX();
    }
}