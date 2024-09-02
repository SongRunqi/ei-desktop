package com.ei.desktop;

import com.ei.desktop.exception.FXMLLoadException;
import com.ei.desktop.stage.StageManager;
import com.ei.desktop.utils.EILog;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;

/**
 * @author yitiansong
 * 2024/6/10-19:45
 */
public class HomeController {

    @FXML
    public VBox menuBox;
    @FXML
    private Pane centerPane;
    /**
     * pin
     */
    private boolean pin = false;
    @FXML
    private void initialize() {
        sentence();  // 默认加载Page 1的内容
    }

    @FXML
    private void pin() {
        StageManager.pinOrNot(pin = !pin);
    }

    public void sentence() {
        loadContent("/com/ei/desktop/sentence/sentence-view.fxml");
    }

//    public void sentenceList() {
//        loadContent("/com/ei/desktop/sentence/sentence-word");
//    }

    private void loadContent(String centerFxml) {
        try {
            URL resource = EI.class.getResource(centerFxml);
            if (resource == null) {
                EILog.logger.error("加载{}文件失败", centerFxml);
                throw new FXMLLoadException("加载" + centerFxml + "文件失败");
            }
            centerPane.getChildren().setAll((Pane) FXMLLoader.load(resource));

        } catch (Exception e) {
            EILog.logger.error("加载文件失败{}", e);
        }
    }

}
