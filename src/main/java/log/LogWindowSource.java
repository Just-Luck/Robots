/**
 * LogWindowSource - источник лога, который хранит и предоставляет доступ к сообщениям лога.
 * Этот класс также поддерживает регистрацию слушателей для отслеживания изменений в логе.
 */
package log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class LogWindowSource {
    private final int m_iQueueLength;
    private final ArrayList<LogEntry> m_messages;
    private final CopyOnWriteArrayList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;

    /**
     * Создает новый источник лога с указанной длиной очереди сообщений.
     *
     * @param iQueueLength - максимальная длина очереди сообщений.
     */
    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<>(iQueueLength);
        m_listeners = new CopyOnWriteArrayList<>();
    }

    /**
     * Регистрирует нового слушателя для отслеживания изменений в логе.
     *
     * @param listener - слушатель для регистрации.
     */
    public void registerListener(LogChangeListener listener) {
        m_listeners.add(listener);
        m_activeListeners = null;
    }

    /**
     * Отменяет регистрацию слушателя, ранее зарегистрированного для отслеживания изменений в логе.
     *
     * @param listener - слушатель для отмены регистрации.
     */
    public void unregisterListener(LogChangeListener listener) {
        m_listeners.remove(listener);
        m_activeListeners = null;
    }

    /**
     * Добавляет новую запись в лог с указанным уровнем и сообщением.
     *
     * @param logLevel - уровень логирования новой записи.
     * @param strMessage - сообщение новой записи.
     */
    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        if (size() >= m_iQueueLength) m_messages.remove(0);
        synchronized (this) {
            m_messages.add(entry);
        }
        LogChangeListener[] activeListeners = m_activeListeners;
        if (activeListeners == null) {
            synchronized (m_listeners) {
                if (m_activeListeners == null) {
                    activeListeners = m_listeners.toArray(new LogChangeListener[0]);
                    m_activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    /**
     * Возвращает текущий размер лога (количество сообщений).
     *
     * @return int - размер лога.
     */
    public synchronized int size() {
        return m_messages.size();
    }

    /**
     * Возвращает диапазон сообщений лога начиная с указанного индекса и заданной длины.
     *
     * @param startFrom - индекс начала диапазона.
     * @param count - количество сообщений в диапазоне.
     * @return Iterable<LogEntry> - диапазон сообщений лога.
     */
    public synchronized Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, size());
        return m_messages.subList(startFrom, indexTo);
    }

    /**
     * Возвращает все сообщения лога.
     *
     * @return Iterable<LogEntry> - все сообщения лога.
     */
    public synchronized Iterable<LogEntry> all() {
        return m_messages;
    }
}