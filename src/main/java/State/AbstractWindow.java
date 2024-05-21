package State;

import java.util.prefs.Preferences;
import javax.swing.JInternalFrame;

/**
 * Абстрактный класс, представляющий оконное приложение, которое может сохранять и загружать свое состояние.
 */
public abstract class AbstractWindow extends JInternalFrame implements WithState {
    private final String windowPreferencesPrefix;
    private final String windowPositionXPrefix;
    private final String windowPositionYPrefix;
    private final String windowSizeWidthPrefix;
    private final String windowSizeHeightPrefix;

    /**
     * Конструктор класса. Инициализирует префиксы для имен ключей.
     */
    public AbstractWindow() {
        super();
        this.windowPreferencesPrefix = formatTitle("window preferences");
        this.windowPositionXPrefix = formatTitle("position x");
        this.windowPositionYPrefix = formatTitle("position y");
        this.windowSizeWidthPrefix = formatTitle("size width");
        this.windowSizeHeightPrefix = formatTitle("size height");
    }

    /**
     * Получает объект Preferences для сохранения и загрузки настроек окна.
     *
     * @return объект Preferences
     */
    private Preferences getPreferences() {
        return Preferences.userRoot().node(windowPreferencesPrefix);
    }

    /**
     * Форматирует заголовок, преобразуя его в верхний регистр и заменяя пробелы на символ подчеркивания.
     *
     * @param title исходный заголовок
     * @return отформатированный заголовок
     */
    private String formatTitle(String title) {
        String upperCaseTitle = title.toUpperCase();
        return upperCaseTitle.replaceAll(" +", "_");
    }

    /**
     * Сохраняет текущее состояние окна в объекте Preferences.
     */
    @Override
    public void saveWindow() {
        Preferences preferences = getPreferences();
        String formattedTitle = formatTitle(getTitle());

        preferences.putInt(windowPositionXPrefix + formattedTitle, getX());
        preferences.putInt(windowPositionYPrefix + formattedTitle, getY());
        preferences.putInt(windowSizeWidthPrefix + formattedTitle, getWidth());
        preferences.putInt(windowSizeHeightPrefix + formattedTitle, getHeight());
    }

    /**
     * Загружает состояние окна из объекта Preferences и восстанавливает его.
     */
    @Override
    public void loadWindow() {
        Preferences preferences = getPreferences();
        final int missing = -1;
        String formattedTitle = formatTitle(getTitle());

        int x = preferences.getInt(windowPositionXPrefix + formattedTitle, missing);
        int y = preferences.getInt(windowPositionYPrefix + formattedTitle, missing);
        int width = preferences.getInt(windowSizeWidthPrefix + formattedTitle, missing);
        int height = preferences.getInt(windowSizeHeightPrefix + formattedTitle, missing);

        if (x == -1 || y == -1 || width == -1 || height == -1) return;

        setBounds(x, y, width, height);
    }
}