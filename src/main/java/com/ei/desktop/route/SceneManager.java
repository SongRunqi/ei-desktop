package com.ei.desktop.route;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author yitiansong
 * 2024/7/7-12:32
 */

import java.io.IOException;
import java.util.ResourceBundle;

public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;
    private ResourceBundle resourceBundle;

    public void setResourceBundle(ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }
    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void loadScene(AppRoute route) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route.getFxmlPath()));
        // ... 使用 this.resourceBundle 加载 FXML ...
        loader.setResources(this.resourceBundle);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(route.getTitle());
    }

    public <T> T loadScene(AppRoute route, Class<T> controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route.getFxmlPath()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(route.getTitle());
        return loader.getController();
    }
}
