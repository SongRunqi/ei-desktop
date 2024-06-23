package com.ei.desktop.sentence;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author yitiansong
 * 2024/6/10-09:09
 * 句子解释场景
 */
public class SentenceExplainController {
    public Button explainButton;
    @FXML
    private TextArea sentence;
//    private Scene scene;
    @FXML
    private TextArea explanation;

    public void explain() {
        explanation.setText("your sentence:" + sentence.getText());
        explanation.visibleProperty().setValue(true);
    }
    @FXML
    private void initialize() {
//        // 按钮隐藏
//        sentence.textProperty().addListener((observable, oldValue, newValue) -> {
//            explainButton.visibleProperty().setValue(!newValue.isEmpty());
//        });
        // 添加场景加载完成后的监听器
        sentence.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) { // 当场景被加载
                setSceneKeyCombination(newScene);
            }
        });


    }

    private void setSceneKeyCombination(Scene scene) {
        // 设置快捷键 Ctrl+E
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        scene.setOnKeyPressed(event -> {
            if (keyComb.match(event)) {
                explain();  // 调用解释方法
            }
        });
    }
}
