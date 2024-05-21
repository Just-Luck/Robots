package model;

import java.awt.geom.Point2D;

/**
 * Класс, представляющий цель для робота.
 */
public class Target
{
    private final Point2D.Double position = new Point2D.Double();

    /**
     * Конструктор класса Target, инициализирующий позицию цели.
     * @param x координата x цели
     * @param y координата y цели
     */
    public Target(double x, double y)
    {
        double scaleFactor = 2f;
        position.setLocation(x * scaleFactor, y * scaleFactor);
    }

    /**
     * Пустой конструктор класса Target.
     */
    public Target() {}

    /**
     * Возвращает позицию цели.
     * @return позиция цели
     */
    public Point2D.Double getPosition()
    {
        return position;
    }
}