package model;

import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, отвечающий за логику управления роботом.
 */
public class RobotsLogic extends Observable
{
    private static final double ANGULAR_VELOCITY = 0.001;
    private static final double TARGET_CLOSE_ENOUGH = 5;
    private final static double EPSILON = 0.05;
    private final Robot robot;
    private Target target;
    private final long deltaTime = 5;
    private Timer timer;
    private Point2D.Double windowBounds = new Point2D.Double(300, 300);

    /**
     * Конструктор класса RobotsLogic, инициализирующий робота и цель.
     */
    public RobotsLogic()
    {
        robot = new Robot();
        target = new Target(50, 50);
        setTarget(target);
        moveRobot();
    }

    /**
     * Запускает таймер для генерации событий.
     */
    public void startTimer()
    {
        timer = new Timer("event generator", true);
        addActionToTimer(new TimerTask()
        {
            @Override
            public void run()
            {
                moveRobot();
                setChanged();
                notifyObservers();
            }
        }, deltaTime);
    }

    /**
     * Двигает робота в направлении цели.
     */
    public void moveRobot()
    {
        if (robot.getPosition().distance(target.getPosition()) < TARGET_CLOSE_ENOUGH) return;

        final double angleRobotTarget = RobotsMath.angleTo(robot.getPosition(), target.getPosition());

        if (Math.abs(robot.getAngularVelocity()) < ANGULAR_VELOCITY || Math.abs(robot.getDirection() - angleRobotTarget) < EPSILON)
        {
            robot.move(new Point2D.Double(
                    robot.getSpeed() * Math.cos(robot.getDirection()) * deltaTime,
                    robot.getSpeed() * Math.sin(robot.getDirection()) * deltaTime
            ));
            return;
        }

        final double newAngle = RobotsMath.asNormalizedRadians(robot.getDirection() + robot.getAngularVelocity() * deltaTime);

        final double dx = robot.getSpeed() / robot.getAngularVelocity() * (Math.sin(newAngle) - Math.sin(robot.getDirection()));
        final double dy = robot.getSpeed() / robot.getAngularVelocity() * (Math.cos(newAngle) - Math.cos(robot.getDirection()));

        robot.move(new Point2D.Double(
                dx * RobotsMath.speedFactor(robot.getPosition().getX(), windowBounds.getX()),
                -dy * RobotsMath.speedFactor(robot.getPosition().getY(), windowBounds.getY())
        ));
        robot.setDirection(newAngle);
    }

    /**
     * Добавляет действие в таймер с указанным таймаутом.
     * @param task действие, которое нужно выполнить
     * @param timeout время между выполнениями действия в миллисекундах
     */
    public void addActionToTimer(TimerTask task, long timeout)
    {
        timer.schedule(task, 0, timeout);
    }

    /**
     * Останавливает таймер.
     */
    public void stopTimer()
    {
        timer.cancel();
    }

    /**
     * Возвращает робота.
     * @return робот
     */
    public Robot getRobot()
    {
        return robot;
    }

    /**
     * Возвращает текущую цель робота.
     * @return цель
     */
    public Target getTarget()
    {
        return target;
    }

    /**
     * Устанавливает новую цель для робота.
     * @param newTarget новая цель
     */
    public void setTarget(Target newTarget)
    {
        this.target = newTarget;

        if (RobotsMath.angleTo(robot.getPosition(), target.getPosition()) > robot.getDirection())
        {
            robot.setAngularVelocity(-ANGULAR_VELOCITY);
        } else
        {
            robot.setAngularVelocity(ANGULAR_VELOCITY);
        }
    }

    /**
     * Устанавливает границы окна.
     * @param newWindowBounds новые границы окна
     */
    public void setWindowBounds(Point2D.Double newWindowBounds)
    {
        this.windowBounds = newWindowBounds;
    }

    /**
     * Вспомогательный класс для математических расчетов, связанных с движением робота.
     */
    private static class RobotsMath
    {
        /**
         * Вычисляет угол между двумя точками.
         * @param p0 первая точка
         * @param p1 вторая точка
         * @return угол между точками в радианах
         */
        public static double angleTo(Point2D.Double p0, Point2D.Double p1)
        {
            final double dx = p1.getX() - p0.getX();
            final double dy = p1.getY() - p0.getY();

            return asNormalizedRadians(Math.atan2(dy, dx));
        }

        /**
         * Нормализует угол в радианах в диапазоне от 0 до 2π.
         * @param angle угол в радианах
         * @return нормализованный угол
         */
        private static double asNormalizedRadians(double angle)
        {
            final double TAU = 2 * Math.PI;

            if (angle < 0)
            {
                return TAU - ((-angle) % TAU);
            }

            return angle % TAU;
        }

        /**
         * Вычисляет коэффициент скорости на основе положения и верхней границы.
         * @param t текущая координата
         * @param upperBoundT верхняя граница координаты
         * @return коэффициент скорости
         */
        private static double speedFactor(double t, double upperBoundT)
        {
            return Math.max(1 - 2 * Math.abs((upperBoundT - t) / upperBoundT - 0.5), 0.01);
        }
    }
}