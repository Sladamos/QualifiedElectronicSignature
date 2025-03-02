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
import pg.proj.pg.signature.provider.SignatureVerifierProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFXSignatureVerifierSelectorController {

    private final Map<String, SignatureVerifierProvider> verifierProviders = new HashMap<>();

    private final NoArgsEvent onCloseStageRequested = new NoArgsEventImpl();

    @Setter
    private ErrorHandlingLayer errorHandlingLayer;

    private SignatureVerifierProvider signatureVerifierProvider;

    @FXML
    private FlowPane buttonsContainer;

    @FXML
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public SignatureVerifierProvider getSelectedSignatureVerifierProvider() {
        if(signatureVerifierProvider == null) {
            throw new BasicAppError("Verifier provider wasn't selected properly");
        }
        return signatureVerifierProvider;
    }

    public void addVerifierProviders(List<SignatureVerifierProvider> newVerifierProviders) {
        var mapOfNewVerifierProviders = newVerifierProviders.stream().collect(Collectors.toMap(
                SignatureVerifierProvider::getUniqueName, (provider) -> provider));
        verifierProviders.putAll(mapOfNewVerifierProviders);
        addProvidersAsRadioButtons(newVerifierProviders);
    }

    public void registerOnCloseStageRequested(NoArgsEventReceiver listener) {
        onCloseStageRequested.addListener(listener);
    }

    @FXML
    protected void onSaveClicked() {
        errorHandlingLayer.runInErrorHandler(this::updateSelectedVerifierProvider);
    }

    private void updateSelectedVerifierProvider() {
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        if(button == null) {
            throw new BasicAppError("Verifier is not selected");
        }
        String key = button.getText();
        signatureVerifierProvider = verifierProviders.get(key);
        onCloseStageRequested.invoke();
        onCloseStageRequested.removeAllListeners();
    }

    private void addProvidersAsRadioButtons(List<SignatureVerifierProvider> newVerifierProviders) {
        newVerifierProviders.stream()
                .map(SignatureVerifierProvider::getUniqueName)
                .map(RadioButton::new)
                .forEach(this::addButtonToContainerWithProperToggleGroup);
    }

    private void addButtonToContainerWithProperToggleGroup(RadioButton radioButton) {
        radioButton.setToggleGroup(toggleGroup);
        radioButton.getStyleClass().add("radio-button");
        buttonsContainer.getChildren().add(radioButton);
    }
}
