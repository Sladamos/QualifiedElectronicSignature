package pg.proj.pg.signature.selector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.signature.provider.SignatureVerifierProvider;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JavaFXSignatureVerifierSelector implements SignatureVerifierSelector {

    private final List<SignatureVerifierProvider> signatureVerifierProviders;

    private final ErrorHandlingLayer errorHandlingLayer;

    @Override
    public SignatureVerifierProvider selectVerifier() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSignatureVerifierSelector.class.getResource("verifier-selector.fxml"));
            Stage stage = initializeGuiAndCreateStage(loader);
            JavaFXSignatureVerifierSelectorController controller = loader.getController();
            initializeControllerWithNecessaryInformation(controller, stage);
            stage.showAndWait();
            return controller.getSelectedSignatureVerifierProvider();
        } catch (IOException | IllegalStateException e) {
            throw new CriticalAppError("Unable to load select verifier scene");
        }
    }

    private void initializeControllerWithNecessaryInformation(JavaFXSignatureVerifierSelectorController controller, Stage stage) {
        controller.addVerifierProviders(signatureVerifierProviders);
        controller.setErrorHandlingLayer(errorHandlingLayer);
        controller.registerOnCloseStageRequested(stage::close);
    }

    private Stage initializeGuiAndCreateStage(FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load(), 512, 384);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Cipher");
        stage.setScene(scene);
        return stage;
    }
}
