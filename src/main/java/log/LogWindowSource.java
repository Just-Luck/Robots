package log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogWindowSource
{
    private final int queueLength;
    private final CircularLogBuffer<LogEntry> messages;
    private final List<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;

    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        this.messages = new CircularLogBuffer<>(queueLength);
        this.listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener)
    {
        synchronized (listeners)
        {
            listeners.add(listener);
            activeListeners = null;
        }
    }

    public void unregisterListener(LogChangeListener listener)
    {
        synchronized (listeners)
        {
            listeners.remove(listener);
            activeListeners = null;
        }
    }

    public void append(LogLevel logLevel, String message)
    {
        LogEntry entry = new LogEntry(logLevel, message);
        messages.append(entry);
        LogChangeListener[] currentListeners = activeListeners;
        if (currentListeners == null)
        {
            synchronized (listeners)
            {
                if (activeListeners == null)
                {
                    currentListeners = listeners.toArray(new LogChangeListener[0]);
                    activeListeners = currentListeners;
                }
            }
        }
        for (LogChangeListener listener : currentListeners)
        {
            listener.onLogChanged();
        }
    }

    public int size()
    {
        return messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= messages.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, messages.size());
        return messages.range(startFrom, indexTo);
    }

    public Iterable<LogEntry> all()
    {
        return messages.all();
    }
}
