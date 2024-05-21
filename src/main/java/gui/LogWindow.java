package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import State.AbstractWindow;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

/**
 * Окно для отображения логов приложения.
 */
public class LogWindow extends AbstractWindow implements LogChangeListener, PropertyChangeListener
{
    private final LogWindowSource logSource;
    private final TextArea logContent;

    /**
     * Конструктор окна логов.
     *
     * @param logSource источник логов
     */
    public LogWindow(LogWindowSource logSource)
    {
        super();

        setTitle("Окно логов");
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);

        this.logSource = logSource;
        this.logContent = new TextArea("");
        this.logContent.setSize(200, 500);

        logSource.registerListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    /**
     * Обновляет содержимое логов.
     */
    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
    }

    /**
     * Обработчик изменений в логах.
     */
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    /**
     * Обработчик изменения языка интерфейса.
     *
     * @param evt событие изменения языка
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if ("changeLocale".equals(evt.getPropertyName()))
        {
            ResourceBundle bundle = (ResourceBundle) evt.getNewValue();
            setTitle(bundle.getString("LogsWindow"));
        }
    }
}