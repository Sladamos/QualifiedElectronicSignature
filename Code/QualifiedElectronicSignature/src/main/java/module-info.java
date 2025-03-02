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
    exports pg.proj.pg.error;
    exports pg.proj.pg.communicate;
    opens pg.proj.pg to javafx.fxml;
    opens pg.proj.pg.cipher.selector to javafx.fxml;
    opens pg.proj.pg.signature.selector to javafx.fxml;
    opens pg.proj.pg.password.selector to javafx.fxml;
    opens pg.proj.pg.error to javafx.fxml;
    opens pg.proj.pg.data.hasher;
    opens pg.proj.pg.cipher.executioner;
    opens pg.proj.pg.data.unlocker;
}