package pg.proj.pg;

import javafx.fxml.FXML;
import lombok.Setter;
import pg.proj.pg.plug.CryptorPlug;

@Setter
public class MainController {

    private CryptorPlug cryptorPlug;

    @FXML
    protected void onEncryptClicked() {
        cryptorPlug.onEncryptCalled();
    }

    @FXML
    protected void onDecryptClicked() {
        cryptorPlug.onDecryptCalled();
    }

}
