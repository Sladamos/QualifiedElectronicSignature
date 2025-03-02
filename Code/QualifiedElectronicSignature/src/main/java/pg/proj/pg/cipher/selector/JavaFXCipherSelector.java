package pg.proj.pg.cipher.selector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JavaFXCipherSelector implements CipherSelector {

    private final List<CipherProvider> cipherProviders;

    private final ErrorHandlingLayer errorHandlingLayer;

    @Override
    public CipherProvider selectCipher() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXCipherSelector.class.getResource("cipher-selector.fxml"));
            Stage stage = initializeGuiAndCreateStage(loader);
            JavaFXCipherSelectorController controller = loader.getController();
            initializeControllerWithNecessaryInformation(controller, stage);
            stage.showAndWait();
            return controller.getSelectedCipherProvider();
        } catch (IOException | IllegalStateException e) {
            throw new CriticalAppError("Unable to load select cipher scene");
        }
    }

    private void initializeControllerWithNecessaryInformation(JavaFXCipherSelectorController controller, Stage stage) {
        controller.addCipherProviders(cipherProviders);
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
