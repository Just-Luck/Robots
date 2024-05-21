package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.TimerTask;
import javax.swing.JPanel;
import model.Robot;
import model.RobotsLogic;
import model.Target;

/**
 * Панель для визуализации игры.
 */
public class GameVisualizer extends JPanel
{
    private final RobotsLogic logic;

    private static final int ROBOT_WIDTH = 30;
    private static final int ROBOT_HEIGHT = 10;
    private static final int EYE_SIZE = 5;
    private static final int TARGET_SIZE = 5;
    private static final Color ROBOT_COLOR = Color.MAGENTA;
    private static final Color ROBOT_BORDER_COLOR = Color.BLACK;
    private static final Color ROBOT_EYE_COLOR = Color.WHITE;
    private static final Color TARGET_COLOR = Color.GREEN;

    /**
     * Создает GameVisualizer с указанной логикой.
     *
     * @param logic игровая логика
     */
    public GameVisualizer(RobotsLogic logic)
    {
        this.logic = logic;

        // Добавление задачи в таймер логики для перерисовки через каждые 50 мс
        logic.addActionToTimer(new TimerTask()
        {
            @Override
            public void run()
            {
                repaint();
            }
        }, 50);

        // Добавление обработчика событий мыши для установки цели по клику
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Point clickPoint = e.getPoint();
                logic.setTarget(new Target(clickPoint.getX(), clickPoint.getY()));
                logic.setWindowBounds(new Point2D.Double(getWidth(), getHeight()));
                repaint();
            }
        });

        setDoubleBuffered(true);
    }

    /**
     * Перерисовывает компонент, отображая робота и цель.
     *
     * @param g графический контекст
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, logic.getRobot());
        drawTarget(g2d, logic.getTarget());
    }

    /**
     * Рисует робота на указанном графическом контексте.
     *
     * @param g графический контекст
     * @param robot робот для рисования
     */
    private void drawRobot(Graphics2D g, Robot robot)
    {
        int robotCenterX = (int) Math.round(robot.getPosition().getX());
        int robotCenterY = (int) Math.round(robot.getPosition().getY());

        AffineTransform transform = AffineTransform.getRotateInstance(robot.getDirection(), robotCenterX, robotCenterY);
        g.setTransform(transform);

        g.setColor(ROBOT_COLOR);
        fillOval(g, robotCenterX, robotCenterY, ROBOT_WIDTH, ROBOT_HEIGHT);
        g.setColor(ROBOT_BORDER_COLOR);
        drawOval(g, robotCenterX, robotCenterY, ROBOT_WIDTH, ROBOT_HEIGHT);

        g.setColor(ROBOT_EYE_COLOR);
        fillOval(g, robotCenterX + 10, robotCenterY, EYE_SIZE, EYE_SIZE);
        g.setColor(ROBOT_BORDER_COLOR);
        drawOval(g, robotCenterX + 10, robotCenterY, EYE_SIZE, EYE_SIZE);
    }

    /**
     * Рисует цель на указанном графическом контексте.
     *
     * @param g графический контекст
     * @param target цель для рисования
     */
    private void drawTarget(Graphics2D g, Target target)
    {
        resetTransform(g);
        g.setColor(TARGET_COLOR);
        fillOval(g, (int) target.getPosition().getX(), (int) target.getPosition().getY(), TARGET_SIZE, TARGET_SIZE);
        g.setColor(ROBOT_BORDER_COLOR);
        drawOval(g, (int) target.getPosition().getX(), (int) target.getPosition().getY(), TARGET_SIZE, TARGET_SIZE);
    }

    /**
     * Сбрасывает текущее преобразование графического контекста.
     *
     * @param g графический контекст
     */
    private void resetTransform(Graphics2D g)
    {
        g.setTransform(new AffineTransform());
    }

    /**
     * Заливает овал указанными параметрами на указанном графическом контексте.
     *
     * @param g графический контекст
     * @param centerX координата X центра овала
     * @param centerY координата Y центра овала
     * @param width ширина овала
     * @param height высота овала
     */
    private static void fillOval(Graphics g, int centerX, int centerY, int width, int height)
    {
        g.fillOval(centerX - width / 2, centerY - height / 2, width, height);
    }

/**
 * Рисует овал указанными параметрами на указанном графическом контексте.
 *
 * @param g графический контекст
 * @param centerX координата X центра овала
 * @param centerY координата Y центра овала
 * @param width ширина овала
 * @param height высота овала
 */
    private static void drawOval(Graphics g, int centerX, int centerY, int width, int height)
    {
        g.drawOval(centerX - width / 2, centerY - height / 2, width, height);
    }
}