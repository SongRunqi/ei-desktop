package com.ei.desktop.controller.login;

import com.ei.desktop.config.AppConfig;
import com.ei.desktop.controller.main.MainViewController;
import com.ei.desktop.route.AppRoute;
import com.ei.desktop.route.SceneManager;
import com.ei.desktop.theme.ColorTheme;
import com.ei.desktop.theme.ThemeManager;
import com.ei.desktop.utils.EILog;
import com.ei.desktop.utils.PreferenceUtils;
import com.ei.desktop.utils.http.LoginHttp;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * 2024/7/7-09:43
 */
public class LoginController {
    @FXML
    private VBox rootVBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    /**
     * 记住我复选框
     */
    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Text actiontarget;

    private final AppConfig config;
    private final Preferences prefs;

    // 主题控制
    private final ThemeManager themeManager = ThemeManager.getInstance();

    /**
     * 初始化
     */
    public LoginController() {
        // 在构造函数中初始化 Preferences
        prefs = Preferences.userNodeForPackage(PreferenceUtils.class);
        // config没有用
        config = AppConfig.getInstance();
    }

    /**
     * 登录按钮
     */
    @FXML
    protected void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        prefs.putBoolean("rememberMe", rememberMeCheckBox.isSelected());
        // 更新登录界面保存的用户名和密码
        if (rememberMeCheckBox.isSelected() && config.isRememberMeEnabled()) {
            prefs.put("username", username);
            prefs.put("password", password);
            // 可以在这里设置过期时间，使用 config.getRememberMeDuration()
        } else {
            prefs.remove("username");
        }
        // 校验输入
        if (validateInput(username, password)) {
            boolean loginSuccess = performLogin(username, password);

            // save setting
            if (loginSuccess) {
                try {
                    // 加载主页面
                    MainViewController mainController = SceneManager.getInstance().loadScene(AppRoute.MAIN, MainViewController.class);
//                    mainController.setLoggedIn(true);
                } catch (Exception e) {
                    EILog.logger.info("程序加载错误{}",e);
                    showError("Error loading main view");
                }
                actiontarget.setText("登录成功");
//                actiontarget.setStyle("-fx-fill: green;");
            } else {
                showError("登录失败：用户名或密码错误");
            }
            // login
//            SceneManager.getInstance().loadScene(AppRoute.MAIN);
        }
    }

    /**
     * 对登录表单进行验证
     * @param username 用户名
     * @param password 密码
     * @return 验证通过与否
     */
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

        if (password.length() < 4) {
            showError("密码长度必须至少为6个字符");
            return false;
        }

        // 可以添加更多的验证规则，比如密码复杂度要求等

        return true;
    }

    /**
     * 初始化逻辑
     */
    @FXML
    public void initialize() {
        initUserInfo();
        initTheme();
    }

    /**
     * 从prefs获取用户名和密码,避免重复输入
     */
    private void initUserInfo() {
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
    }

    /**
     * 自行登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功与否
     */
    private boolean performLogin(String username, String password) {
        return LoginHttp.login(username, password);
    }

    /**
     * 显示错误消息
     * @param message 消息
     */
    private void showError(String message) {
        actiontarget.setText(message);
//        actiontarget.setStyle("-fx-fill: red;");
    }

    /**
     * 应用主题
     * @param theme 颜色主题
     */
    private void applyTheme(ColorTheme theme) {
        rootVBox.setStyle(String.format(
                "-fx-primary: %s; -fx-secondary: %s; -fx-accent: %s; " +
                        "-fx-light-bg: %s; -fx-lightest-bg: %s; -fx-text-color: %s;",
                theme.getPrimary(), theme.getSecondary(), theme.getAccent(),
                theme.getLightBackground(), theme.getLightestBackground(), theme.getTextColor()
        ));
    }

    /**
     * 初始化颜色主题
     */
    private void initTheme() {
        applyTheme(themeManager.getCurrentTheme());

        themeManager.currentThemeProperty().addListener((obs, oldTheme, newTheme) -> {
            applyTheme(newTheme);
        });

//        switchThemeButton.setOnAction(event -> {
//            if (themeManager.getCurrentTheme() instanceof PurpleTheme) {
//                themeManager.setCurrentTheme(new BlueTheme());
//            } else {
//                themeManager.setCurrentTheme(new PurpleTheme());
//            }
//        });
    }

}
