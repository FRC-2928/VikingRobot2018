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
        if (instance == null)
        {
            instance = new NotifierManager();
        }
        return instance;
    }
    public Notifier runNotifer(Runnable command, double timeStep)
    {
        Notifier n = new Notifier(command);
        n.startPeriodic(timeStep);
        notifiers.add(n);
        return n;
    }

    public void stopAllNotifiers()
    {
        for(int i = 0; i < notifiers.size(); i++)
        {
            notifiers.get(i).stop();
        }
        notifiers.removeAll(notifiers);
    }
}
