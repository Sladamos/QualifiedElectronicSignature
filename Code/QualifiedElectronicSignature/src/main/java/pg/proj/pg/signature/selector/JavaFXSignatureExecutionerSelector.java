package pg.proj.pg.signature.selector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.signature.provider.SignatureExecutionerProvider;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JavaFXSignatureExecutionerSelector implements SignatureExecutionerSelector {

    private final List<SignatureExecutionerProvider> signatureExecutionerProviders;

    private final ErrorHandlingLayer errorHandlingLayer;

    @Override
    public SignatureExecutionerProvider selectExecutioner() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSignatureExecutionerSelector.class.getResource("executioner-selector.fxml"));
            Stage stage = initializeGuiAndCreateStage(loader);
            JavaFXSignatureExecutionerSelectorController controller = loader.getController();
            initializeControllerWithNecessaryInformation(controller, stage);
            stage.showAndWait();
            return controller.getSelectedSignatureExecutionerProvider();
        } catch (IOException | IllegalStateException e) {
            throw new CriticalAppError("Unable to load select cipher scene");
        }
    }

    private void initializeControllerWithNecessaryInformation(JavaFXSignatureExecutionerSelectorController controller, Stage stage) {
        controller.addExecutionerProviders(signatureExecutionerProviders);
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
