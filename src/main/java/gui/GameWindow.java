/**
 * GameWindow - внутреннее окно, представляющее игровое окно в графическом пользовательском интерфейсе.
 * Оно содержит в себе компонент GameVisualizer для отображения игровой сцены.
 */
package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame
{

    /**
     * Создает новое игровое окно.
     *
     * @param bundle - ResourceBundle, содержащий локализованные строки для заголовка игрового окна.
     * @param width - ширина игрового окна.
     * @param height - высота игрового окна.
     */
    public GameWindow(ResourceBundle bundle, int width, int height)
    {
        super(bundle.getString("gameWindowHeader"), true, true, true, true);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(width, height);
    }
}