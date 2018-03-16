package org.usfirst.frc.team2928.Subsystem.Chassis;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem {

    public Drivetrain drivetrain;
    public Transmission transmission;

    public Chassis()
    {
        drivetrain = new Drivetrain();
        transmission = new Transmission();
    }

    @Override
    public void initDefaultCommand()
    {

    }
}
