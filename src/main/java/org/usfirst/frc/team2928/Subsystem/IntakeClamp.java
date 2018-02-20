package org.usfirst.frc.team2928.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2928.RobotMap;

public class IntakeClamp extends Subsystem {

    public enum GripperState{
        OPEN,
        CLOSED;

        public GripperState toggle() {
            return this.equals(CLOSED) ? GripperState.OPEN : GripperState.CLOSED;
        }

    }

    private Solenoid gripper;

    @Override
    protected void initDefaultCommand() {

    }

    public IntakeClamp() {
        gripper = new Solenoid(RobotMap.SOLENOID_INTAKE);
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