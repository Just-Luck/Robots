package gui;

import java.awt.Frame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotsProgram
{

  private static final Logger logger = Logger.getLogger(RobotsProgram.class.getName());

  public static void main(String[] args)
  {
    setLookAndFeel();
    SwingUtilities.invokeLater(() ->
    {
      MainApplicationFrame frame = new MainApplicationFrame();
      frame.pack();
      frame.setVisible(true);
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    });
  }

  /**
   * Устанавливает внешний вид Nimbus для приложения.
   */
  private static void setLookAndFeel()
  {
    try
    {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
    {
      logger.log(Level.SEVERE, "Не удалось установить Nimbus LookAndFeel", e);
    }
  }
}
