/**
 * MainApplicationFrame - основное окно приложения.
 * Оно содержит в себе внутренние окна, меню и обработчики событий.
 */
package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ResourceBundle bundle;

    /**
     * Создает новое основное окно приложения.
     *
     * @param defaultBundle - ResourceBundle по умолчанию, содержащий локализованные строки.
     * @param inset - величина отступа от края экрана.
     */
    public MainApplicationFrame(ResourceBundle defaultBundle, int inset)
    {
        // Отступы окна от края экрана
        bundle = defaultBundle;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        // Добавление внутренних окон
        addWindow(createLogWindow());
        addWindow(new GameWindow(bundle, 400, 400));

        // Настройка меню
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitConfirm();
            }
        });

    }

    /**
     * Создает окно для отображения лога.
     *
     * @return LogWindow - созданное окно лога.
     */
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), bundle);
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(bundle.getString("protocolWork"));
        return logWindow;
    }

    /**
     * Добавляет внутреннее окно на рабочий стол.
     *
     * @param frame - внутреннее окно для добавления.
     */
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Генерирует строку меню для приложения.
     *
     * @return JMenuBar - строка меню приложения.
     */
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());
        menuBar.add(createExitMenu());

        return menuBar;
    }

    /**
     * Создает меню выбора внешнего вида приложения.
     *
     * @return JMenu - меню выбора внешнего вида.
     */
    private JMenu createLookAndFeelMenu()
    {
        JMenu lookAndFeelMenu = new JMenu(bundle.getString("lookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                bundle.getString("lookAndFeelMenuDescription"));

        JMenuItem systemLookAndFeel = new JMenuItem(bundle.getString("systemScheme"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(bundle.getString("universalScheme"), KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    /**
     * Создает тестовое меню для добавления записей в лог.
     *
     * @return JMenu - тестовое меню для добавления записей в лог.
     */
    private JMenu createTestMenu()
    {
        JMenu testMenu = new JMenu(bundle.getString("testMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(bundle.getString("testMenuDescription"));

        JMenuItem addLogMessageItem = new JMenuItem(bundle.getString("logItem"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> Logger.debug(bundle.getString("logDefaultMessage")));
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    /**
     * Создает меню для выхода из приложения.
     *
     * @return JMenu - меню для выхода из приложения.
     */
    private JMenu createExitMenu()
    {
        JMenu exitMenu = new JMenu(bundle.getString("quit"));
        exitMenu.setMnemonic(KeyEvent.VK_X);
        JMenuItem exitMenuItem = new JMenuItem(bundle.getString("ExitTheApplication"), KeyEvent.VK_S);
        exitMenuItem.addActionListener((event) -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        exitMenu.add(exitMenuItem);
        return exitMenu;
    }

    /**
     * Подтверждение выхода из приложения с выводом диалогового окна.
     */
    private void ExitConfirm()
    {
        int confirm = JOptionPane.showOptionDialog(this,
                bundle.getString("quitQuestion"),
                bundle.getString("quitTitle"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{bundle.getString("yes"), bundle.getString("no")},
                null);
        if (confirm == JOptionPane.YES_OPTION) setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Устанавливает внешний вид приложения.
     *
     * @param className - имя класса внешнего вида.
     */
    private void setLookAndFeel(String className)
    {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}