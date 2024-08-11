package com.ei.desktop.theme;

import com.ei.desktop.utils.PreferenceUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * @since 2024/8/11
 */

public class ThemeManager {
    private static ThemeManager instance;
    private final ObjectProperty<ColorTheme> currentTheme = new SimpleObjectProperty<>();
    private final Preferences prefs = Preferences.userNodeForPackage(PreferenceUtils.class);
    private final Logger logger = LoggerFactory.getLogger(ThemeManager.class);
    private ThemeManager() {
        updateThemeBasedOnSystem();
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public ColorTheme getCurrentTheme() {
        return currentTheme.get();
    }

    public ObjectProperty<ColorTheme> currentThemeProperty() {
        return currentTheme;
    }

    public void updateThemeBasedOnSystem() {
        boolean isDarkMode = detectSystemTheme();
        setTheme(isDarkMode ? new PurpleTheme() : new BlueTheme());
    }

    private boolean detectSystemTheme() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return isWindowsDarkMode();
        } else if (os.contains("mac")) {
            return isMacOSDarkMode();
        } else {
            // 对于其他操作系统，我们可以提供一个默认值或者让用户手动选择
            return prefs.getBoolean("darkMode", false);
        }
    }

    private boolean isWindowsDarkMode() {
        try {
            // 使用注册表检查 Windows 深色模式设置
            Process process = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("AppsUseLightTheme") && line.contains("0x0")) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Error reading Windows registry", e);
        }
        return false;
    }

    private boolean isMacOSDarkMode() {
        try {
            // 使用 AppleScript 检查 macOS 深色模式设置
            Process process = Runtime.getRuntime().exec(new String[]{"osascript", "-e", "tell application \"System Events\" to tell appearance preferences to get dark mode"});
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String result = reader.readLine();
            return "true".equalsIgnoreCase(result);
        } catch (Exception e) {
            logger.error("Error reading macOS settings", e);
        }

        return false;
    }

    private void setTheme(ColorTheme theme) {
        currentTheme.set(theme);
    }

    // 提供一个方法让用户手动切换主题
    public void toggleTheme() {
        boolean currentIsDark = getCurrentTheme() instanceof PurpleTheme;
        setTheme(currentIsDark ? new BlueTheme() : new PurpleTheme());
        prefs.putBoolean("darkMode", !currentIsDark);
    }
}

