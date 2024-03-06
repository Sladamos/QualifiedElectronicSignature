package pg.proj.pg.cipher.selector;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.event.entity.api.NoArgsEvent;
import pg.proj.pg.event.entity.impl.NoArgsEventImpl;
import pg.proj.pg.event.receiver.api.NoArgsEventReceiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFXCipherSelectorController {

    private final Map<String, CipherProvider> cipherProviders = new HashMap<>();

    private final NoArgsEvent onCloseStageRequested = new NoArgsEventImpl();

    @Setter
    private ErrorHandlingLayer errorHandlingLayer;

    private CipherProvider selectedCipherProvider;

    @FXML
    private FlowPane buttonsContainer;

    @FXML
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public CipherProvider getSelectedCipherProvider() {
        if(selectedCipherProvider == null) {
            throw new BasicAppError("Cipher provider wasn't selected properly");
        }
        return selectedCipherProvider;
    }

    public void addCipherProviders(List<CipherProvider> newCipherProviders) {
        var mapOfNewCipherProviders = newCipherProviders.stream().collect(Collectors.toMap(CipherProvider::getUniqueName, (provider) -> provider));
        cipherProviders.putAll(mapOfNewCipherProviders);
        addProvidersAsRadioButtons(newCipherProviders);
    }

    public void registerOnCloseStageRequested(NoArgsEventReceiver listener) {
        onCloseStageRequested.addListener(listener);
    }

    @FXML
    protected void onSaveClicked() {
        errorHandlingLayer.runInErrorHandler(this::updateSelectedCipherProvider);
    }

    private void updateSelectedCipherProvider() {
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        if(button == null) {
            throw new BasicAppError("Cipher is not selected");
        }
        String key = button.getText();
        selectedCipherProvider = cipherProviders.get(key);
        onCloseStageRequested.invoke();
        onCloseStageRequested.removeAllListeners();
    }

    private void addProvidersAsRadioButtons(List<CipherProvider> newCipherProviders) {
        newCipherProviders.stream()
                .map(CipherProvider::getUniqueName)
                .map(RadioButton::new)
                .forEach(this::addButtonToContainerWithProperToggleGroup);
    }

    private void addButtonToContainerWithProperToggleGroup(RadioButton radioButton) {
        radioButton.setToggleGroup(toggleGroup);
        radioButton.getStyleClass().add("radio-button");
        buttonsContainer.getChildren().add(radioButton);
    }
}
