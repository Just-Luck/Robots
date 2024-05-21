package log;

public final class Logger
{
    private static final LogWindowSource defaultLogSource;

    static
    {
        defaultLogSource = new LogWindowSource(5);
    }

    private Logger()
    {
    }

    public static void debug(String message)
    {
        defaultLogSource.append(LogLevel.Debug, message);
    }

    public static void error(String message)
    {
        defaultLogSource.append(LogLevel.Error, message);
    }

    public static LogWindowSource getDefaultLogSource()
    {
        return defaultLogSource;
    }
}