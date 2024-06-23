module com.ei.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.logging.log4j;
    opens com.ei.desktop to javafx.fxml, javafx.graphics;
    opens com.ei.desktop.sentence to javafx.fxml;
    exports com.ei.desktop;
}