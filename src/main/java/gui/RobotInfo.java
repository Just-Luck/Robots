package gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JLabel;

import State.AbstractWindow;
import model.RobotsLogic;

public class RobotInfo extends AbstractWindow implements Observer, PropertyChangeListener {
    private  final JLabel label;

    public RobotInfo(RobotsLogic logic) {
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

    @Override
    public void update(Observable o, Object arg) {
        RobotsLogic lg = (RobotsLogic) o;
        label.setText("x=%f y=%f dir=%f".formatted(lg.getRobot().getPosition().getX(),
                lg.getRobot().getPosition().getY(), lg.getRobot().getDirection()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("changeLocale".equals(evt.getPropertyName())){
            ResourceBundle bundle = (ResourceBundle)evt.getNewValue();
            setTitle(bundle.getString("Coordinates"));
        }
    }
}