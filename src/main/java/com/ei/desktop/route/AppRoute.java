package com.ei.desktop.route;

import java.util.Arrays;

/**
 * @author yitiansong
 * 2024/7/7-12:31
 */
public enum AppRoute {
    LOGIN("/views/login/login.fxml", "english improver"),
    MAIN("/views/main/MainView.fxml", "main"),
    SETTINGS("/views/SettingsView.fxml", "setting");
    /**
     * fxml路径
     */
    private final String fxmlPath;
    /**
     * 页面名称
     */
    private final String title;

    AppRoute(String fxmlPath, String title) {
        this.fxmlPath = fxmlPath;
        this.title = title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getTitle() {
        return title;
    }

    public static AppRoute getByName(String title) {
        return Arrays.stream(AppRoute.values()).filter(route -> route.getTitle().equals(title)).toList().getFirst();
    }
}
