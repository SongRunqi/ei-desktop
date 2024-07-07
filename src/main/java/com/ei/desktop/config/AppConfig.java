package com.ei.desktop.config;

import java.io.*;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();
    private static AppConfig instance = null;

    private AppConfig() {
        loadConfig();
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private void loadConfig() {
        // 首先尝试从类路径加载
        try (InputStream inputStream = AppConfig.class.getResourceAsStream("/" + CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果类路径中没有，尝试从文件系统加载
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 如果文件不存在，设置默认值
            setDefaults();
        }
    }

    private void setDefaults() {
        properties.setProperty("rememberMe.enabled", "true");
        properties.setProperty("rememberMe.duration", "30");
        properties.setProperty("app.theme", "light");
        properties.setProperty("app.language", "en");
        saveConfig();
    }

    public void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Application Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter 和 Setter 方法...

    public boolean isRememberMeEnabled() {
        return Boolean.parseBoolean(properties.getProperty("rememberMe.enabled", "true"));
    }

    public void setRememberMeEnabled(boolean enabled) {
        properties.setProperty("rememberMe.enabled", String.valueOf(enabled));
        saveConfig();
    }

    public int getRememberMeDuration() {
        return Integer.parseInt(properties.getProperty("rememberMe.duration", "30"));
    }

    public void setRememberMeDuration(int days) {
        properties.setProperty("rememberMe.duration", String.valueOf(days));
        saveConfig();
    }
}