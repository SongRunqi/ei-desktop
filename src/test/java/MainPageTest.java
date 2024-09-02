/**
 * @author yitiansong
 * @since 2024/8/12
 */

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPageTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

            // 设置 SceneManager 的 primaryStage
            SceneManager.getInstance().setPrimaryStage(primaryStage);

            // 加载资源束以支持国际化
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());
            SceneManager.getInstance().setResourceBundle(bundle);
            // 设置资源束到 SceneManager

            // 加载登录页面
            SceneManager.getInstance().loadScene(AppRoute.LOGIN);


            // 显示窗口
            primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
