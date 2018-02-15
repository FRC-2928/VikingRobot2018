package org.usfirst.frc.team2928.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.text.FieldPosition;

public class SmartAuto extends Command
{
    @Override
    public boolean isFinished()
    {
        return false;
    }

    public enum Position
    {
        LEFT,
        MIDDLE,
        RIGHT,
        ERROR;

        static Position fromLetter(char l)
        {
            switch(l)
            {
                case 'L' : return LEFT;
                case 'M' : return MIDDLE;
                case 'R' : return RIGHT;
                default: return ERROR;
            }
        }
    }


    private Position nearSwitch;
    private Position scale;
    private Position farSwitch;

    public SmartAuto(String fieldState)
    {

    }

    private boolean parseFieldState(String fieldState)
    {
        try
        {
            nearSwitch = Position.fromLetter(fieldState.charAt(0));
            scale = Position.fromLetter(fieldState.charAt(1));
            farSwitch = Position.fromLetter(fieldState.charAt(2));
        } catch (StringIndexOutOfBoundsException e)
        {
            return false;
        }
        return true;
    }
}
