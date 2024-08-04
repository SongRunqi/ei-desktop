package com.ei.desktop;

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import com.ei.desktop.utils.EILog;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.ei.desktop.utils.http.LoginHttp.checkLoginStatus;

/**
 * @author yitiansong
 */
public class EI extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // 设置 SceneManager 的 primaryStage
            SceneManager.getInstance().setPrimaryStage(primaryStage);

            // 加载资源束以支持国际化
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());

            // 设置资源束到 SceneManager
            SceneManager.getInstance().setResourceBundle(bundle);
            if (checkLoginStatus()) {
                // 加载登录页面
                SceneManager.getInstance().loadScene(AppRoute.MAIN);
            } else {
                SceneManager.getInstance().loadScene(AppRoute.LOGIN);
            }

            // 显示窗口
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("An error occurred while starting the application:");
            EILog.logger.error(e.getStackTrace());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}