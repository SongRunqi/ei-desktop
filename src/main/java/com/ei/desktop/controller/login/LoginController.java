package com.ei.desktop.controller.login;

import com.ei.desktop.config.AppConfig;
import com.ei.desktop.controller.main.MainViewController;
import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * 2024/7/7-09:43
 */
public class LoginController {
    @FXML
    public Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private AppConfig config;
    /**
     * 记住我复选框
     */
    @FXML
    private CheckBox rememberMeCheckBox;

    private Preferences prefs;
    @FXML
    private Text actiontarget;

    public LoginController() {
        // 在构造函数中初始化 Preferences
        prefs = Preferences.userNodeForPackage(LoginController.class);
        config = AppConfig.getInstance();
    }


    @FXML
    protected void handleSubmitButtonAction() {
        actiontarget.setText("登录按钮被点击");
        System.out.println("用户名: " + usernameField.getText());
        System.out.println("密码: " + passwordField.getText());
        // 这里添加实际的登录逻辑
    }

    @FXML
    protected void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateInput(username, password)) {
            boolean loginSuccess = performLogin(username, password);

            // save setting
            if (loginSuccess) {
                prefs.putBoolean("rememberMe", rememberMeCheckBox.isSelected());
                if (rememberMeCheckBox.isSelected() && config.isRememberMeEnabled()) {
                    prefs.put("username", username);
                    prefs.put("password", password);
                    // 可以在这里设置过期时间，使用 config.getRememberMeDuration()
                } else {
                    prefs.remove("username");
                }
                actiontarget.setText("登录成功");
                actiontarget.setStyle("-fx-fill: green;");
            } else {
                showError("登录失败：用户名或密码错误");
            }
            // login
            SceneManager.getInstance().loadScene(AppRoute.MAIN);
        }
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            showError("用户名不能为空");
            return false;
        }

        if (username.length() < 4) {
            showError("用户名长度必须至少为4个字符");
            return false;
        }

        if (password.isEmpty()) {
            showError("密码不能为空");
            return false;
        }

        if (password.length() < 6) {
            showError("密码长度必须至少为6个字符");
            return false;
        }

        // 可以添加更多的验证规则，比如密码复杂度要求等

        return true;
    }
    // 为用户名和密码字段添加验证逻辑
    @FXML
    public void initialize() {
        // 加载保存的用户名（如果有）
        boolean rememberMe = prefs.getBoolean("rememberMe", false);
        if (rememberMe) {
            String savedUsername = prefs.get("username", "");
            String savedPassword = prefs.get("password", "");
            if (!savedUsername.isEmpty()) {
                usernameField.setText(savedUsername);
                rememberMeCheckBox.setSelected(true);
            }
            if (!savedPassword.isEmpty()) {
                passwordField.setText(savedPassword);
            }
        }else {
            rememberMeCheckBox.setSelected(false);
        }

        // 为用户名和密码字段添加验证逻辑
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameField.setStyle("-fx-border-color: red;");
            } else {
                usernameField.setStyle("");
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 6) {
                passwordField.setStyle("-fx-border-color: red;");
            } else {
                passwordField.setStyle("");
            }
        });
    }

    private boolean performLogin(String username, String password) {
        // 这里应该是实际的登录逻辑
        // 为了演示，我们只是简单地检查用户名和密码是否非空
        return !username.isEmpty() && !password.isEmpty();
    }
    private void showError(String message) {
        actiontarget.setText(message);
        actiontarget.setStyle("-fx-fill: red;");
    }
}
