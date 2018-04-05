package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team2928.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommandGroupBuilder {
    private class CommandEntry
    {
        public Command command;
        public double timeout;
        public boolean sequential;

        public CommandEntry(Command command, double timeout, boolean sequential)
        {
            this.command = command;
            this.timeout = timeout;
            this.sequential = sequential;
        }
    }
    private List<CommandEntry> commands;

    private class BuiltCommandGroup extends CommandGroup {
        BuiltCommandGroup(List<CommandEntry> commands) {
            for (CommandEntry c : commands) {
                if (c.sequential)
                {
                    if (c.timeout >= 0)
                        addSequential(c.command, c.timeout);
                    else
                        addSequential(c.command);
                } else
                {
                    if (c.timeout >= 0)
                        addParallel(c.command, c.timeout);
                    else
                        addParallel(c.command);
                }
            }
        }
    }

    public CommandGroupBuilder() {
        commands = new ArrayList<>();
    }

    public CommandGroupBuilder addSequential(Command command, double timeout) {
        commands.add(new CommandEntry(command, timeout, true));
        return this;
    }

    public CommandGroupBuilder addSequential(Command command) {
        return addSequential(command, -1);
    }

    public CommandGroupBuilder addParallel(Command command, double timeout)
    {
        commands.add(new CommandEntry(command, timeout, false));
        return this;
    }

    public CommandGroupBuilder addParallel(Command command)
    {
        addParallel(command, -1);
        return this;
    }

    public CommandGroupBuilder delay(double timeout) {
        return addSequential(new WaitCommand(timeout));
    }

    public CommandGroup build() {
        return new BuiltCommandGroup(commands);
    }
}