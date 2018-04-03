package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2928.Pair;

import java.util.ArrayList;
import java.util.List;

public class SequentialCommandGroupBuilder {
    private List<Pair<Command, Double>> commands;

    private class BuiltCommandGroup extends CommandGroup {
        BuiltCommandGroup(List<Pair<Command, Double>> commands) {
            for (Pair<Command, Double> c : commands) {
                if (c.getValue() >= 0)
                    addSequential(c.getKey(), c.getValue());
                else
                    addSequential(c.getKey());
            }
        }
    }

    public SequentialCommandGroupBuilder() {
        commands = new ArrayList<>();
    }

    public SequentialCommandGroupBuilder addCommand(Command command, double timeout) {
        commands.add(new Pair<>(command, timeout));
        return this;
    }

    public SequentialCommandGroupBuilder addCommand(Command command) {
        return addCommand(command, -1);
    }

    public SequentialCommandGroupBuilder delay(double timeout) {
        return addCommand(new WaitCommand(timeout));
    }

    public CommandGroup build() {
        return new BuiltCommandGroup(commands);
    }
}