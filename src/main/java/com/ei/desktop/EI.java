package com.ei.desktop;

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author yitiansong
 */
public class EI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            // 设置 SceneManager 的 primaryStage
            SceneManager.getInstance().setPrimaryStage(primaryStage);

            // 加载资源束以支持国际化
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());

            // 设置资源束到 SceneManager
            SceneManager.getInstance().setResourceBundle(bundle);

            // 加载登录页面
            SceneManager.getInstance().loadScene(AppRoute.MAIN);

            // 显示窗口
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("An error occurred while starting the application:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}