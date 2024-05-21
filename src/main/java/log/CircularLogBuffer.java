package log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Кольцевой буфер для хранения журнала записей логов.
 * @param <T> тип элементов, хранящихся в буфере
 */
public class CircularLogBuffer<T>
{
    private final T[] buffer;
    private int size;
    private int start;
    private int end;
    private final ReentrantLock lock;
    private final Condition notFull;
    private final Condition notEmpty;

    /**
     * Конструктор кольцевого буфера.
     * @param capacity емкость буфера
     */
    public CircularLogBuffer(int capacity)
    {
        buffer = (T[]) new Object[capacity];
        size = 0;
        start = 0;
        end = 0;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    /**
     * Добавляет запись в буфер.
     * @param entry запись для добавления
     */
    public void append(LogEntry entry)
    {
        lock.lock();
        try
        {
            if (size == buffer.length)
            {
                start = (start + 1) % buffer.length;
                size--;
            }
            buffer[end] = (T) entry;
            end = (end + 1) % buffer.length;
            size++;
            notEmpty.signal();
        } finally
        {
            lock.unlock();
        }
    }

    /**
     * Возвращает диапазон записей из буфера.
     * @param startFrom начальный индекс
     * @param count количество записей
     * @return список записей в указанном диапазоне
     */
    public Iterable<LogEntry> range(int startFrom, int count)
    {
        lock.lock();
        try
        {
            if (startFrom < 0 || startFrom >= size)
            {
                return Collections.emptyList();
            }
            int index = (start + startFrom) % buffer.length;
            List<LogEntry> result = new ArrayList<>();
            for (int i = 0; i < count && i < size; i++)
            {
                result.add((LogEntry) buffer[index]);
                index = (index + 1) % buffer.length;
            }
            return result;
        } finally
        {
            lock.unlock();
        }
    }

    /**
     * Возвращает размер буфера.
     * @return размер буфера
     */
    public int size()
    {
        lock.lock();
        try
        {
            return size;
        } finally
        {
            lock.unlock();
        }
    }

    /**
     * Возвращает все записи из буфера.
     * @return список всех записей в буфере
     */
    public List<LogEntry> all()
    {
        lock.lock();
        try
        {
            List<LogEntry> result = new ArrayList<>();
            for (int i = 0; i < size; i++)
            {
                result.add((LogEntry) buffer[(start + i) % buffer.length]);
            }
            return result;
        } finally
        {
            lock.unlock();
        }
    }

    /**
     * Очищает буфер.
     */
    public void clear()
    {
        lock.lock();
        try
        {
            Arrays.fill(buffer, null);
            size = 0;
            start = 0;
            end = 0;
            notFull.signalAll();
        } finally
        {
            lock.unlock();
        }
    }
}