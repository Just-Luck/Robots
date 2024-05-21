package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import State.AbstractWindow;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;


public class LogWindow extends AbstractWindow implements LogChangeListener, PropertyChangeListener
{
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super();

        setTitle("Окно логов");
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);

        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("changeLocale".equals(evt.getPropertyName())){
            ResourceBundle bundle = (ResourceBundle)evt.getNewValue();
            setTitle(bundle.getString("LogsWindow"));
        }
    }
}