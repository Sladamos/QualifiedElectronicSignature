module pg.proj.pg {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.commons.lang3;

    opens pg.proj.pg to javafx.fxml;
    exports pg.proj.pg;
}