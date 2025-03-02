package pg.proj.pg.password.selector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.password.provider.PasswordProvider;

import java.io.IOException;

@AllArgsConstructor
public class JavaFXPasswordSelector implements PasswordSelector {

    private final ErrorHandlingLayer errorHandlingLayer;

    @Override
    public PasswordProvider selectPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXPasswordSelector.class.getResource("password-selector.fxml"));
            Stage stage = initializeGuiAndCreateStage(loader);
            JavaFXPasswordSelectorController controller = loader.getController();
            initializeControllerWithNecessaryInformation(controller, stage);
            stage.showAndWait();
            return controller.getSelectedPasswordProvider();
        } catch (IOException | IllegalStateException e) {
            throw new CriticalAppError("Unable to load select password scene");
        }
    }

    private void initializeControllerWithNecessaryInformation(JavaFXPasswordSelectorController controller, Stage stage) {
        controller.setErrorHandlingLayer(errorHandlingLayer);
        controller.registerOnCloseStageRequested(stage::close);
    }

    private Stage initializeGuiAndCreateStage(FXMLLoader loader)  throws IOException {
        Scene scene = new Scene(loader.load(), 512, 384);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Provide password");
        stage.setScene(scene);
        return stage;
    }
}
