package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2928.Match;

import java.text.FieldPosition;

public class SmartAuto extends Command
{

    public enum AutoTarget
    {
        SCALE,
        SWITCH,
        LINE
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    public void goForSwitch()
    {

    }

    public SmartAuto()
    {

    }
}
