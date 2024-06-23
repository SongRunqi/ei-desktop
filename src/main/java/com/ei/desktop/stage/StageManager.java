package com.ei.desktop.stage;

import javafx.stage.Stage;

/**
 * @author yitiansong
 * 2024/6/12-23:52
 */
public class StageManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }
//    public static Stage getPrimaryStage() {
//        return primaryStage;
//    }

    public static void pinOrNot(boolean pin) {
        primaryStage.setAlwaysOnTop(pin);
    }
}
