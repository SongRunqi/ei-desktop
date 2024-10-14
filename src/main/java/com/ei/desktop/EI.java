package com.ei.desktop;

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import com.ei.desktop.utils.PreferenceUtils;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            // 设置图标
            primaryStage.getIcons().add(new Image("fav.jpg"));
            // 加载资源束以支持国际化
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());

            // 设置资源束到 SceneManager,并根据登录状态选择加载主页面或者登录页面
            SceneManager.getInstance().setResourceBundle(bundle);
            if (checkLoginStatus(PreferenceUtils.get("username"))) {
                SceneManager.getInstance().loadScene(AppRoute.MAIN);
            } else {
                SceneManager.getInstance().loadScene(AppRoute.LOGIN);
            }

            // 显示窗口
            primaryStage.show();
        } catch (Exception e) {
            logger.error("程序加载异常,", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}