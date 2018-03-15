package org.usfirst.frc.team2928;

import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;
import java.util.List;

public class NotifierManager {
    private static NotifierManager instance;
    private List<Notifier> notifiers;

    protected NotifierManager()
    {
        notifiers = new ArrayList<>();
    }

    public static NotifierManager getInstance()
    {
        if (instance != null)
            return instance;
        return instance = new NotifierManager();
    }

    public Notifier runNotifier(Runnable runnable, double interval)
    {
        Notifier notifier = new Notifier(runnable);
        notifier.startPeriodic(interval);
        return notifier;
    }

    public void stopAll()
    {
        for (int i = notifiers.size() - 1; i >= 0; i--) {
            notifiers.get(i).stop();
            notifiers.remove(i);
        }
    }
}
