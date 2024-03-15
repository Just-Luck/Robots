/**
 * Logger - предоставляет методы для логирования сообщений различных уровней.
 * По умолчанию используется статический источник лога.
 */
package log;

public final class Logger
{
    // Статический источник лога по умолчанию
    private static final LogWindowSource defaultLogSource;
    static {
        defaultLogSource = new LogWindowSource(100);
    }

    private Logger()
    {
    }

    /**
     * Записывает сообщение лога с уровнем Debug.
     *
     * @param strMessage - сообщение для записи.
     */
    public static void debug(String strMessage)
    {
        defaultLogSource.append(LogLevel.Debug, strMessage);
    }

    /**
     * Записывает сообщение лога с уровнем Error.
     *
     * @param strMessage - сообщение для записи.
     */
    public static void error(String strMessage)
    {
        defaultLogSource.append(LogLevel.Error, strMessage);
    }

    /**
     * Возвращает статический источник лога по умолчанию.
     *
     * @return LogWindowSource - статический источник лога по умолчанию.
     */
    public static LogWindowSource getDefaultLogSource()
    {
        return defaultLogSource;
    }
}