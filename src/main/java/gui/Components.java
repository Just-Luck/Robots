/**
 * Класс Components предоставляет метод для перевода свойств компонентов, таких как тексты кнопок и подсказки,
 * для внутренних рамок в графическом интерфейсе Swing с использованием ResourceBundle для локализации.
 */
package gui;

import java.util.ResourceBundle;
import javax.swing.*;

public class Components
{
    /**
     * Переводит свойства компонентов, такие как тексты кнопок и подсказки,
     * для внутренних рамок в графическом интерфейсе Swing с использованием ResourceBundle для локализации.
     *
     * @param bundle ResourceBundle, содержащий локализованные строки для кнопок и подсказок.
     */
    public static void translateСomponents(ResourceBundle bundle)
    {
        // Устанавливаем подсказки для кнопок внутренних рамок
        UIManager.put("InternalFrame.iconButtonToolTip", bundle.getString("minimize"));
        UIManager.put("InternalFrame.maxButtonToolTip", bundle.getString("maximize"));
        UIManager.put("InternalFrame.closeButtonToolTip", bundle.getString("close"));

        // Устанавливаем тексты кнопок для панели заголовка внутренних рамок
        UIManager.put("InternalFrameTitlePane.restoreButtonText", bundle.getString("restore"));
        UIManager.put("InternalFrameTitlePane.moveButtonText", bundle.getString("move"));
        UIManager.put("InternalFrameTitlePane.sizeButtonText", bundle.getString("size"));
        UIManager.put("InternalFrameTitlePane.minimizeButtonText", bundle.getString("minimize"));
        UIManager.put("InternalFrameTitlePane.maximizeButtonText", bundle.getString("maximize"));
        UIManager.put("InternalFrameTitlePane.closeButtonText", bundle.getString("close"));
    }
}