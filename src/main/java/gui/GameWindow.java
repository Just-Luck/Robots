package gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import State.AbstractWindow;
import model.RobotsLogic;

/**
 * Окно игры, в котором отображается игровой процесс.
 */
public class GameWindow extends AbstractWindow implements PropertyChangeListener
{

    private final RobotsLogic logic;

    /**
     * Конструктор игрового окна.
     *
     * @param logic логика роботов
     */
    public GameWindow(RobotsLogic logic)
    {
        super();

        this.logic = logic;
        logic.startTimer();

        setTitle("Игровое окно");
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);

        GameVisualizer gameVisualizer = new GameVisualizer(logic);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    /**
     * Освобождает ресурсы и останавливает таймер логики.
     */
    @Override
    public void dispose()
    {
        super.dispose();
        logic.stopTimer();
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
            setTitle(bundle.getString("NewGameWindow"));
        }
    }
}