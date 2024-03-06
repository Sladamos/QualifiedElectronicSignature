package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.provider.EncryptedRsaCipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.cipher.selector.JavaFXCipherSelector;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.error.layer.ErrorHandlingLayerImpl;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.plug.CryptorPlug;
import pg.proj.pg.plug.CryptorPlugImpl;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.file.selector.JavaFXFileSelector;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Emulator");
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        initializeVariables(controller, stage);
        stage.show();
    }

    private void initializeVariables(MainController controller, Stage stage) {;
        MainReceiver receiver = createMainReceiver(controller);
        setErrorHandlingLayer(controller, receiver);
        setCryptorPlug(controller, stage);
    }

    private MainReceiver createMainReceiver(MainController controller) {
        return new MainReceiver(controller::onErrorOccurred, controller::onExitCalled);
    }

    private void setErrorHandlingLayer(MainController controller, MainReceiver receiver) {
        ErrorHandlingLayer errorHandlingLayer = new ErrorHandlingLayerImpl();
        errorHandlingLayer.registerBasicErrorReceiver(receiver);
        errorHandlingLayer.registerCriticalErrorReceiver(receiver);
        controller.setErrorHandlingLayer(errorHandlingLayer);
    }

    private void setCryptorPlug(MainController controller, Stage stage) {
        FileSelector fileSelector = new JavaFXFileSelector(stage, Set.of(FileExtension.CPP, FileExtension.TXT));
        List<CipherProvider> encryptCipherProviders = List.of(new EncryptedRsaCipherProvider(null));
        CipherSelector encryptCipherSelector = new JavaFXCipherSelector(encryptCipherProviders); //TODO
        CryptorPlug cryptorPlug = new CryptorPlugImpl(fileSelector, encryptCipherSelector);
        controller.setCryptorPlug(cryptorPlug);
    }

    public static void main(String[] args) {
        launch();
    }

}