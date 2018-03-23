package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import javafx.util.Pair;

import java.util.List;

public class SequentialCommandGroupBuilder {
    private List<Pair<Command, Double>> commands;

    private class BuiltCommandGroup extends CommandGroup
    {
        BuiltCommandGroup(List<Pair<Command, Double>> commands)
        {
            for (Pair<Command, Double> c : commands)
            {
                addSequential(c.getKey(), c.getValue());
            }
        }
    }

    public SequentialCommandGroupBuilder addCommand(Command command, double timeout)
    {
        commands.add(new Pair<>(command, timeout));
        return this;
    }

    public SequentialCommandGroupBuilder addCommand(Command command)
    {
        return addCommand(command, -1);
    }

    public SequentialCommandGroupBuilder wait(double timeout)
    {
        addCommand(new WaitCommand(timeout));
        return this;
    }

    public CommandGroup build()
    {
        return new BuiltCommandGroup(commands);
    }
}
