module com.ei.desktop {
    // 必要的依赖
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.logging.log4j;
    requires java.prefs;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    // 导出主包
    exports com.ei.desktop;
    // 将该包暴露给jackson
    exports com.ei.desktop.domain to com.fasterxml.jackson.databind;
    exports com.ei.desktop.dto to com.fasterxml.jackson.databind;
    // 导出并开放控制器包
    exports com.ei.desktop.controller.login;
    opens com.ei.desktop.controller.login to javafx.fxml;
    opens com.ei.desktop.controller.main to javafx.fxml;
    // 开放主包和视图包给JavaFX
    opens com.ei.desktop to javafx.fxml, javafx.graphics;
    opens views.login to javafx.fxml;
    opens views.main to javafx.fxml;

    // 开放国际化资源包
    opens i18n;
}