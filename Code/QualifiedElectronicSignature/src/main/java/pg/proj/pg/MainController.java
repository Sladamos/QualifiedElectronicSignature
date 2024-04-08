package pg.proj.pg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import lombok.Setter;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.plug.CryptorPlug;
import pg.proj.pg.plug.SignerPlug;

import java.util.Objects;

@Setter
public class MainController {

    private ErrorHandlingLayer errorHandlingLayer;

    private CryptorPlug cryptorPlug;

    private SignerPlug signerPlug;

    @FXML
    private Label communicateLabel;

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
        communicateLabel.setText("");
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }

    public void onCommunicateOccurred(String content) {
        communicateLabel.setText(content);
    }

    @FXML
    private void onEncryptClicked() {
        //errorHandlingLayer.runInErrorHandler(cryptorPlug::onEncryptCalled);
        errorHandlingLayer.runInErrorHandler(signerPlug::onSignCalled);
    }

    @FXML
    private void onDecryptClicked() {
        errorHandlingLayer.runInErrorHandler(cryptorPlug::onDecryptCalled);
    }
}
