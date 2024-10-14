package com.ei.desktop.route;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author yitiansong
 * 场景管理器,负责i18n和更换场景
 */
public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;
    private ResourceBundle resourceBundle;
    private final Logger logger = LoggerFactory.getLogger(SceneManager.class);
    /**
     * 禁止外部实例化该类
     */
    private SceneManager() {}

    /**
     * 只能通过该方法获取该类的实例
     * @return SceneManager
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * 根据路由名称加载场景
     * @param name 路由Approute对应的title
     * @see AppRoute
     */
    public void loadScene(String name) {
        AppRoute byName = AppRoute.getByName(name);
        loadScene(byName);
    }

    /**
     * 根据路由加载fxml
     * @param route 路由
     */
    public void loadScene(AppRoute route) {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(route.getFxmlPath()));
        // ... 使用 this.resourceBundle 加载 FXML ...
        loader.setResources(resourceBundle);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            logger.error("eee",e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(route.getTitle());
    }

    /**
     * 加载场景
     * @param route 路由
     * @param controllerClass 控制类
     * @return 该路由的控制器
     * @param <T> 控制类类型
     * @throws IOException io异常
     */
    public <T> T loadScene(AppRoute route, Class<T> controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route.getFxmlPath()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(route.getTitle());
        return loader.getController();
    }

    /**
     * 设置资源
     * @param bundle 资源
     */
    public void setResourceBundle(ResourceBundle bundle) {
        resourceBundle = bundle;
    }
}
