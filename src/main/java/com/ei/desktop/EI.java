package com.ei.desktop;

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import com.ei.desktop.utils.EILog;
import com.ei.desktop.utils.PreferenceUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.ei.desktop.utils.http.LoginHttp.checkLoginStatus;

/**
 * @author yitiansong
 */
public class EI extends Application {

    private final Logger logger = LoggerFactory.getLogger(EI.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            // 设置 SceneManager 的 primaryStage
            SceneManager.getInstance().setPrimaryStage(primaryStage);

            // 加载资源束以支持国际化
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());

            // 设置资源束到 SceneManager
            SceneManager.getInstance().setResourceBundle(bundle);
            if (checkLoginStatus(PreferenceUtils.get("username"))) {
                // 加载登录页面
                SceneManager.getInstance().loadScene(AppRoute.MAIN);
            } else {
                SceneManager.getInstance().loadScene(AppRoute.LOGIN);
            }

            // 显示窗口
            primaryStage.show();
        } catch (Exception e) {
            logger.error("程序加载异常,", e);
            // 使用logger打印异常堆栈
            Throwable t = e;
            while (t != null) {
                logger.error("错误消息{}", t.getMessage());
                t = t.getCause();
            }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}