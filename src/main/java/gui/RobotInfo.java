package gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import java.util.logging.Logger;

import State.AbstractWindow;
import model.RobotsLogic;

/**
 * Окно для отображения информации о роботе, включая его координаты и направление.
 */
public class RobotInfo extends AbstractWindow implements Observer, PropertyChangeListener
{
    private static final Logger logger = Logger.getLogger(RobotInfo.class.getName());
    private final JLabel label;

    /**
     * Конструктор окна информации о роботе.
     * @param logic логика роботов
     */
    public RobotInfo(RobotsLogic logic)
    {
        super();
        this.label = new JLabel();
        setTitle("Координаты");

        logic.addObserver(this);
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);

        getContentPane().add(label, BorderLayout.CENTER);
        pack();
    }

    /**
     * Обновляет информацию о положении и направлении робота.
     * @param o объект Observable
     * @param arg дополнительные аргументы
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof RobotsLogic)
        {
            RobotsLogic lg = (RobotsLogic) o;
            var position = lg.getRobot().getPosition();
            var direction = lg.getRobot().getDirection();
            label.setText(String.format("x=%.2f y=%.2f dir=%.2f", position.getX(), position.getY(), direction));
        } else
        {
            logger.warning("Observable is not an instance of RobotsLogic");
        }
    }

    /**
     * Изменяет заголовок окна при смене локали.
     * @param evt объект PropertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if ("changeLocale".equals(evt.getPropertyName()))
        {
            if (evt.getNewValue() instanceof ResourceBundle)
            {
                ResourceBundle bundle = (ResourceBundle) evt.getNewValue();
                setTitle(bundle.getString("Coordinates"));
            } else
            {
                logger.warning("New value is not an instance of ResourceBundle");
            }
        }
    }
}