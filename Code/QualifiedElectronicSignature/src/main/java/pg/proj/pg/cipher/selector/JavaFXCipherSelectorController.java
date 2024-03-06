package pg.proj.pg.cipher.selector;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.event.entity.api.NoArgsEvent;
import pg.proj.pg.event.entity.impl.NoArgsEventImpl;
import pg.proj.pg.event.receiver.api.NoArgsEventReceiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFXCipherSelectorController {

    private final Map<String, CipherProvider> cipherProviders = new HashMap<>();

    private final NoArgsEvent onCloseStageRequested = new NoArgsEventImpl();;

    private CipherProvider selectedCipherProvider;

    @FXML
    private ToggleGroup toggleGroup;

    public CipherProvider getSelectedCipherProvider() {
        if(selectedCipherProvider == null) {
            throw new BasicAppError("Cipher provider wasn't selected properly");
        }
        return selectedCipherProvider;
    }

    public void addCipherProviders(List<CipherProvider> newCipherProviders) {
        var mapOfNewCipherProviders = newCipherProviders.stream().collect(Collectors.toMap(CipherProvider::getUniqueName, (provider) -> provider));
        cipherProviders.putAll(mapOfNewCipherProviders);
    }

    public void registerOnCloseStageRequested(NoArgsEventReceiver listener) {
        onCloseStageRequested.addListener(listener);
    }

    @FXML
    protected void onSaveClicked() {
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        String key = button.getText();
        System.out.println(key); //TODO remove
        selectedCipherProvider = cipherProviders.get(key);
        onCloseStageRequested.invoke();
        onCloseStageRequested.removeAllListeners();
    }
}
