/**
 * LogChangeListener - интерфейс для слушателя изменений в логе.
 */
package log;

public interface LogChangeListener
{
    /**
     * Вызывается при изменении лога.
     */
    public void onLogChanged();
}