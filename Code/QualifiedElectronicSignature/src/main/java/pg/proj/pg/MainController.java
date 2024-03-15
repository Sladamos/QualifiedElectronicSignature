package pg.proj.pg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.Setter;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.plug.CryptorPlug;

import java.util.Objects;

@Setter
public class MainController {

    private ErrorHandlingLayer errorHandlingLayer;

    private CryptorPlug cryptorPlug;

    public void onExitCalled() {
        Platform.exit();
    }

    public void onErrorOccurred(String reason) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStyleClass().add("dark-blue-container");
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(reason);
        ButtonType buttonTypeOk = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }

    @FXML
    protected void onEncryptClicked() {
        errorHandlingLayer.runInErrorHandler(cryptorPlug::onEncryptCalled);
    }

    @FXML
    protected void onDecryptClicked() {
        errorHandlingLayer.runInErrorHandler(cryptorPlug::onDecryptCalled);
    }

}
