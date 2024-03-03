package pg.proj.pg;

import javafx.fxml.FXML;
import lombok.Setter;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.plug.CryptorPlug;

@Setter
public class MainController {

    private ErrorHandlingLayer errorHandlingLayer;

    private CryptorPlug cryptorPlug;

    @FXML
    protected void onEncryptClicked() {
        errorHandlingLayer.runInErrorHandler(cryptorPlug::onEncryptCalled);

    }

    @FXML
    protected void onDecryptClicked() {
        errorHandlingLayer.runInErrorHandler(cryptorPlug::onDecryptCalled);
    }

}
