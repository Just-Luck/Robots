/**
 * LogEntry - представляет запись в логе с определенным уровнем логирования.
 */
package log;

public class LogEntry
{
    private LogLevel m_logLevel;
    private String m_strMessage;

    /**
     * Создает новую запись лога с указанным уровнем и сообщением.
     *
     * @param logLevel - уровень логирования.
     * @param strMessage - сообщение лога.
     */
    public LogEntry(LogLevel logLevel, String strMessage)
    {
        m_strMessage = strMessage;
        m_logLevel = logLevel;
    }

    /**
     * Возвращает сообщение лога.
     *
     * @return String - сообщение лога.
     */
    public String getMessage()
    {
        return m_strMessage;
    }

    /**
     * Возвращает уровень логирования.
     *
     * @return LogLevel - уровень логирования.
     */
    public LogLevel getLevel()
    {
        return m_logLevel;
    }
}