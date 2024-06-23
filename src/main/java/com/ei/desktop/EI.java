package com.ei.desktop;

import com.ei.desktop.stage.StageManager;
import com.ei.desktop.utils.EILog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author yitiansong
 */
public class EI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 加载home-view.fxml文件
        try {
            // 加载FXML文件
            FXMLLoader fxmlLoader = new FXMLLoader(EI.class.getResource("/com/ei/desktop/home/home-view.fxml"));
            // 设置场景和舞台
            Scene scene = new Scene(fxmlLoader.load(),550, 420);
            StageManager.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EI");
            primaryStage.show();
        } catch (Exception e) {
            EILog.logger.error("加载home-view.fxml文件失败", e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}