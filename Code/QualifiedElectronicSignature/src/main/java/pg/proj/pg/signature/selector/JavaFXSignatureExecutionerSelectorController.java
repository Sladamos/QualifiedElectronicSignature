package pg.proj.pg.signature.selector;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import lombok.Setter;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.event.NoArgsEvent;
import pg.proj.pg.event.NoArgsEventImpl;
import pg.proj.pg.event.NoArgsEventReceiver;
import pg.proj.pg.signature.provider.SignatureExecutionerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFXSignatureExecutionerSelectorController {

    private final Map<String, SignatureExecutionerProvider> executionerProviders = new HashMap<>();

    private final NoArgsEvent onCloseStageRequested = new NoArgsEventImpl();

    @Setter
    private ErrorHandlingLayer errorHandlingLayer;

    private SignatureExecutionerProvider signatureExecutionerProvider;

    @FXML
    private FlowPane buttonsContainer;

    @FXML
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public SignatureExecutionerProvider getSelectedSignatureExecutionerProvider() {
        if(signatureExecutionerProvider == null) {
            throw new BasicAppError("Signer provider wasn't selected properly");
        }
        return signatureExecutionerProvider;
    }

    public void addExecutionerProviders(List<SignatureExecutionerProvider> newExecutionerProviders) {
        var mapOfNewExecutionerProviders = newExecutionerProviders.stream().collect(Collectors.toMap(
                SignatureExecutionerProvider::getUniqueName, (provider) -> provider));
        executionerProviders.putAll(mapOfNewExecutionerProviders);
        addProvidersAsRadioButtons(newExecutionerProviders);
    }

    public void registerOnCloseStageRequested(NoArgsEventReceiver listener) {
        onCloseStageRequested.addListener(listener);
    }

    @FXML
    protected void onSaveClicked() {
        errorHandlingLayer.runInErrorHandler(this::updateSelectedExecutionerProvider);
    }

    private void updateSelectedExecutionerProvider() {
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        if(button == null) {
            throw new BasicAppError("Executioner is not selected");
        }
        String key = button.getText();
        signatureExecutionerProvider = executionerProviders.get(key);
        onCloseStageRequested.invoke();
        onCloseStageRequested.removeAllListeners();
    }

    private void addProvidersAsRadioButtons(List<SignatureExecutionerProvider> newExecutionerProviders) {
        newExecutionerProviders.stream()
                .map(SignatureExecutionerProvider::getUniqueName)
                .map(RadioButton::new)
                .forEach(this::addButtonToContainerWithProperToggleGroup);
    }

    private void addButtonToContainerWithProperToggleGroup(RadioButton radioButton) {
        radioButton.setToggleGroup(toggleGroup);
        radioButton.getStyleClass().add("radio-button");
        buttonsContainer.getChildren().add(radioButton);
    }
}
