package pg.proj.pg.password.selector;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import lombok.Setter;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.event.entity.api.NoArgsEvent;
import pg.proj.pg.event.entity.impl.NoArgsEventImpl;
import pg.proj.pg.event.receiver.api.NoArgsEventReceiver;
import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.password.provider.PasswordProvider;
import pg.proj.pg.password.provider.PasswordProviderImpl;

public class JavaFXPasswordSelectorController {

    private final NoArgsEvent onCloseStageRequested = new NoArgsEventImpl();

    @Setter
    private ErrorHandlingLayer errorHandlingLayer;

    private PasswordProvider selectedPasswordProvider;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onSaveClicked() {
        errorHandlingLayer.runInErrorHandler(this::changePasswordProvider);
    }

    public PasswordProvider getSelectedPasswordProvider() {
        if(selectedPasswordProvider == null) {
            throw new BasicAppError("Password wasn't set properly");
        }
        return selectedPasswordProvider;
    }

    public void registerOnCloseStageRequested(NoArgsEventReceiver listener) {
        onCloseStageRequested.addListener(listener);
    }

    private void changePasswordProvider() {
        String password = passwordField.getText();
        PasswordInfo passwordInfo = new PasswordInfo(password);
        selectedPasswordProvider = new PasswordProviderImpl(passwordInfo);
        onCloseStageRequested.invoke();
        onCloseStageRequested.removeAllListeners();
    }
}
