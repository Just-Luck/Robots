package model;

import java.awt.geom.Point2D;

/**
 * Класс, представляющий робота.
 */
public class Robot
{
    private final Point2D.Double position = new Point2D.Double(100, 100);
    private double direction = 0;
    private double angularVelocity = 0;
    private final double speed = 0.1;

    /**
     * Перемещает робота на указанное смещение.
     * @param displacement смещение, на которое нужно переместить робота
     */
    public void move(Point2D.Double displacement)
    {
        position.setLocation(getPosition().getX() + displacement.getX(), getPosition().getY() + displacement.getY());
    }

    /**
     * Возвращает скорость робота.
     * @return скорость робота
     */
    public double getSpeed()
    {
        return speed;
    }

    /**
     * Возвращает текущую позицию робота.
     * @return текущая позиция робота
     */
    public Point2D.Double getPosition()
    {
        return position;
    }

    /**
     * Возвращает текущее направление движения робота.
     * @return текущее направление движения робота
     */
    public double getDirection()
    {
        return direction;
    }

    /**
     * Устанавливает новое направление движения робота.
     * @param newDirection новое направление движения робота
     */
    public void setDirection(double newDirection)
    {
        this.direction = newDirection;
    }

    /**
     * Устанавливает новую угловую скорость робота.
     * @param newAngularVelocity новая угловая скорость робота
     */
    public void setAngularVelocity(double newAngularVelocity)
    {
        this.angularVelocity = newAngularVelocity;
    }

    /**
     * Возвращает текущую угловую скорость робота.
     * @return текущая угловая скорость робота
     */
    public double getAngularVelocity()
    {
        return angularVelocity;
    }
}