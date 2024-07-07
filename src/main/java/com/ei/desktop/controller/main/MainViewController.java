package com.ei.desktop.controller.main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;

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

    @FXML
    private void handleTranslate() {
        String inputText = inputTextArea.getText();
        String translatedText = translateText(inputText);
        translationTextArea.setText(translatedText);
        historyListView.getItems().add(0, inputText);
    }

    @FXML
    private void handleTextSelection(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // 双击事件
            String selectedText = inputTextArea.getSelectedText();
            if (!selectedText.isEmpty()) {
                createWordCard(selectedText);
            }
        } else {
            // 单击事件，用于处理拖动选择
            String selectedText = inputTextArea.getSelectedText();
            if (!selectedText.isEmpty()) {
                createWordCard(selectedText);
            }
        }
    }

    private void createWordCard(String text) {
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
        wordDetailsFlowPane.getChildren().add(0, card); // 添加到开头
    }

    private String getWordDefinition(String word) {
        // 这里应该调用你的词典服务来获取单词或短语的定义
        return "Definition of '" + word + "' goes here.";
    }

    private String translateText(String text) {
        // 实现你的翻译逻辑
        return "Translated: " + text;
    }
}