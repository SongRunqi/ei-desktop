package com.ei.desktop.controller.main;

import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * MainViewController
 * 主视图控制器
 * 用于处理主视图的用户交互逻辑
 * @author yitiansong
 */
public class MainViewController {

    @FXML private TextArea inputTextArea;
    @FXML private TextArea translationTextArea;
    @FXML private FlowPane wordDetailsFlowPane;
    @FXML private ListView<String> historyListView;
    @FXML private Button loginButton;
    @FXML private VBox wordDetailsPane;
    @FXML private ListView<String> wordHistoryListView;
    private ObservableList<String> wordHistory = FXCollections.observableArrayList();

    private boolean isWordDetailsVisible = true;
    /**
     * 用户头像
     */
    @FXML private Circle userAvatar;
    /**
     * 登录状态
     */
    private boolean isLoggedIn = false;


    /**
     * 处理翻译逻辑
     */
    @FXML
    private void handleTranslate() {
        String inputText = inputTextArea.getText();
        String translatedText = translateText(inputText);
        translationTextArea.setText(translatedText);
        historyListView.getItems().add(0, inputText);
    }

    /**
     * 查找单词
     * @param word
     */
    private void lookupWord(String word) {
        // 这里添加查词逻辑
        String definition = "Definition of '" + word + "' goes here."; // 替换为实际的查词逻辑

        // 添加到单词历史
        if (!wordHistory.contains(word)) {
            wordHistory.add(0, word);
        }

        // 创建和添加单词卡片
        VBox wordCard = createWordCard(word);
//        wordHistoryListView.getChildren().add(0, wordCard);
        wordDetailsFlowPane.getChildren().add(0, wordCard);
    }
    /**
     * 处理选中
     * @param event
     */
    @FXML
    private void handleTextSelection(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // 双击事件
            String selectedText = inputTextArea.getSelectedText();
            if (!selectedText.isEmpty()) {
//                createWordCard(selectedText);
                lookupWord(selectedText);
            }
        } else {
            // 单击事件，用于处理拖动选择
            String selectedText = inputTextArea.getSelectedText();
            if (!selectedText.isEmpty()) {
//                createWordCard(selectedText);
                lookupWord(selectedText);
            }
        }
    }

    /**
     * 初始化方法
     */
    @FXML
    private void initialize() {
        updateLoginStatus();
    }

    /**
     * 登录
     */
    @FXML
    private void handleLogin() {
        if (!isLoggedIn) {
            try {
                // 打开登录页面
                SceneManager.getInstance().loadScene(AppRoute.LOGIN);
            } catch (Exception e) {
                e.printStackTrace();
//                showError("Error opening login page");
            }
        } else {
            // 登出逻辑
            isLoggedIn = false;
            updateLoginStatus();
        }
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
        updateLoginStatus();
    }

    private void updateLoginStatus() {
        if (isLoggedIn) {
            loginButton.setText("Logout");
            // 设置用户头像
//            Image avatarImage = new Image(getClass().getResourceAsStream("/images/user-avatar.png"));
//            userAvatar.setFill(new ImagePattern(avatarImage));
        } else {
            loginButton.setText("Login");
            // 设置默认头像
//            Image defaultAvatar = new Image(getClass().getResourceAsStream("/images/default-avatar.png"));
//            userAvatar.setFill(new ImagePattern(defaultAvatar));
        }
    }
    private VBox createWordCard(String text) {
        VBox card = new VBox(5);
        card.getStyleClass().add("word-card");

        Label wordLabel = new Label(text);
        wordLabel.getStyleClass().add("word");

        Label definitionLabel = new Label(getWordDefinition(text));
        definitionLabel.getStyleClass().add("definition");

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> wordDetailsFlowPane.getChildren().remove(card));

        HBox headerBox = new HBox(5);
        headerBox.getChildren().addAll(wordLabel, removeButton);

        card.getChildren().addAll(headerBox, definitionLabel);
        return card;
    }



    /**
     * 隐藏或展示单词详情
     */
    @FXML
    private void toggleWordDetails() {
        isWordDetailsVisible = !isWordDetailsVisible;
        wordDetailsPane.setManaged(isWordDetailsVisible);
        wordDetailsPane.setVisible(isWordDetailsVisible);
    }
    /**
     * 获取单词定义
     * @param word
     * @return
     */
    private String getWordDefinition(String word) {
        // 这里应该调用你的词典服务来获取单词或短语的定义
        return "Definition of '" + word + "' goes here.";
    }

    /**
     * 清空所有单词详情
     */
    @FXML
    private void clearAllWordDetails() {
        wordDetailsFlowPane.getChildren().clear();
    }
    /**
     *
     * @param text
     * @return
     */
    private String translateText(String text) {
        // 实现你的翻译逻辑
        return "Translated: " + text;
    }
}