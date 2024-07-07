package com.ei.desktop.route;

/**
 * @author yitiansong
 * 2024/7/7-12:31
 */
public enum AppRoute {
    LOGIN("/views/login/login.fxml", "login"),
    MAIN("/views/main/MainView.fxml", "main"),
    SETTINGS("/views/SettingsView.fxml", "setting");

    private final String fxmlPath;
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
}
