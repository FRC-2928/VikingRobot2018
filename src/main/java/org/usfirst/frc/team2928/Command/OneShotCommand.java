package org.usfirst.frc.team2928.Command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

// Turns a Runnable into a Command
public class OneShotCommand extends Command {
    private Runnable runnable;

    public OneShotCommand(Runnable runnable, Subsystem[] requirements)
    {
        this.runnable = runnable;
        for (Subsystem s : requirements)
            requires(s);
    }

    public OneShotCommand(Runnable runnable, Subsystem requirement)
    {
        this(runnable, new Subsystem[] {requirement});
    }

    public OneShotCommand(Runnable runnable)
    {
        this(runnable, new Subsystem[] {});
    }

    public void initialize()
    {
        runnable.run();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
