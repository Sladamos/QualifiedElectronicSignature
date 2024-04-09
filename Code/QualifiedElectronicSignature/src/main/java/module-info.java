module pg.proj.pg {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.commons.lang3;
    requires usbdrivedetector;
    requires java.xml;

    exports pg.proj.pg;
    exports pg.proj.pg.error.receiver.api;
    exports pg.proj.pg.error.definition;
    exports pg.proj.pg.communicate.receiver.api;
    exports pg.proj.pg.communicate.definition;
    opens pg.proj.pg to javafx.fxml;
    opens pg.proj.pg.cipher.selector to javafx.fxml;
    opens pg.proj.pg.signature.selector to javafx.fxml;
    opens pg.proj.pg.password.selector to javafx.fxml;
    opens pg.proj.pg.error.receiver.api to javafx.fxml;
    opens pg.proj.pg.error.definition to javafx.fxml;
}