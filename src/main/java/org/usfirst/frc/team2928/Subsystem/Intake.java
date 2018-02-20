package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class Intake extends Subsystem {

    public enum GripperState{
        OPEN,
        CLOSED;

        public GripperState toggle() {
            return this.equals(CLOSED) ? GripperState.OPEN : GripperState.CLOSED;
        }

        }

    private Solenoid gripper;
    private WPI_TalonSRX angleMotor;
    // 2 intakes
    private Talon intakeLeft;
    private Talon intakeRight;

    @Override
    protected void initDefaultCommand() {

    }

    public Intake() {
        gripper = new Solenoid(RobotMap.SOLENOID_INTAKE);
        angleMotor = new WPI_TalonSRX(RobotMap.TALON_INTAKE_ANGLE);
        intakeLeft = new Talon(RobotMap.TALON_INTAKE_LEFT);
        intakeRight = new Talon(RobotMap.TALON_INTAKE_RIGHT);
    }

    public void setIntake(double power)
    {
        for (Talon t : new Talon[] {intakeLeft, intakeRight})
            t.set(-power);
    }

    public void open(){
        gripper.set(false);
    }

    public void close(){
        gripper.set(true);
    }

    private void setGripper(GripperState state){
        gripper.set(state.equals(GripperState.OPEN));
    }

    private GripperState getGripperState() {
        return gripper.get() ? GripperState.OPEN : GripperState.CLOSED;
    }

    public void toggle() {
        setGripper(getGripperState().toggle());
    }


}