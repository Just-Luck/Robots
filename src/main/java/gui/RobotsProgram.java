/**
 * RobotsProgram - основной класс программы, запускающий приложение.
 * Здесь устанавливается внешний вид приложения, локализация и создается основное окно приложения.
 */
package gui;

import java.awt.Frame;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram
{
  /**
   * Точка входа в программу.
   *
   * @param args - аргументы командной строки (не используются).
   */
  public static void main(String[] args)
  {
    // Загрузка ресурсов локализации
    ResourceBundle bundle = ResourceBundle.getBundle("ru");
    // Перевод компонентов пользовательского интерфейса
    Components.translateСomponents(bundle);
    try {
      // Установка внешнего вида приложения
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Создание и отображение основного окна приложения
    SwingUtilities.invokeLater(() -> {
      MainApplicationFrame frame = new MainApplicationFrame(bundle, 50);
      frame.pack();
      frame.setVisible(true);
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    });
  }
}