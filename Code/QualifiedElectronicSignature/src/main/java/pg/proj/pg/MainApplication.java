package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pg.proj.pg.cipher.container.CipherContainerImpl;
import pg.proj.pg.cipher.generator.KeyGenerator;
import pg.proj.pg.cipher.generator.PrivateRsaKeyGenerator;
import pg.proj.pg.cipher.generator.PublicRsaKeyGenerator;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.provider.EncryptedCipherProvider;
import pg.proj.pg.cipher.provider.PlainCipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.cipher.selector.JavaFXCipherSelector;
import pg.proj.pg.crypto.encryptor.FileEncryptor;
import pg.proj.pg.crypto.encryptor.SmallFilesEncryptor;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.error.layer.ErrorHandlingLayerImpl;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.operator.SmallFilesContentOperator;
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

    private void initializeVariables(MainController controller, Stage stage) {
        ;
        MainReceiver receiver = createMainReceiver(controller);
        ErrorHandlingLayer errorHandlingLayer = createErrorHandlingLayer(receiver);
        CryptorPlug cryptorPlug = createCryptorPlug(stage, errorHandlingLayer);
        controller.setErrorHandlingLayer(errorHandlingLayer);
        controller.setCryptorPlug(cryptorPlug);
    }

    private MainReceiver createMainReceiver(MainController controller) {
        return new MainReceiver(controller::onErrorOccurred, controller::onExitCalled);
    }

    private ErrorHandlingLayer createErrorHandlingLayer(MainReceiver receiver) {
        ErrorHandlingLayer errorHandlingLayer = new ErrorHandlingLayerImpl();
        errorHandlingLayer.registerBasicErrorReceiver(receiver);
        errorHandlingLayer.registerCriticalErrorReceiver(receiver);
        return errorHandlingLayer;
    }

    private CryptorPlug createCryptorPlug(Stage stage, ErrorHandlingLayer errorHandlingLayer) {
        FileSelector fileSelector = new JavaFXFileSelector(stage, Set.of(FileExtension.CPP, FileExtension.TXT));
        FileSelector cipherFileSelector = new JavaFXFileSelector(stage, Set.of(FileExtension.TXT));
        FileContentOperator cipherFileContentOperator = new SmallFilesContentOperator();
        CipherSelector encryptCipherSelector = createEncryptCipherSelector(errorHandlingLayer,
                cipherFileSelector, cipherFileContentOperator);
        CipherSelector decryptCipherSelector = createDecryptCipherSelector(errorHandlingLayer,
                cipherFileSelector, cipherFileContentOperator);
        FileEncryptor encryptor = createEncryptor();
        return new CryptorPlugImpl(fileSelector, encryptCipherSelector,
                decryptCipherSelector, encryptor);
    }

    private FileEncryptor createEncryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesEncryptor(contentOperator);
    }

    private CipherSelector createEncryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGenerator rsaKeyGenerator = new PublicRsaKeyGenerator();
        List<CipherProvider> encryptCipherProviders = List.of(
                new EncryptedCipherProvider("RSA",
                        () -> CipherContainerImpl.createFromFile(cipherFileSelector,
                                cipherFileContentOperator, rsaKeyGenerator, "RSA")));
        return new JavaFXCipherSelector(encryptCipherProviders, errorHandlingLayer);
    }

    private CipherSelector createDecryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGenerator rsaKeyGenerator = new PrivateRsaKeyGenerator();
        List<CipherProvider> decryptCipherProviders = List.of(
                new PlainCipherProvider("RSA",
                        () -> CipherContainerImpl.createFromFile(cipherFileSelector,
                                cipherFileContentOperator, rsaKeyGenerator, "RSA")));
        return new JavaFXCipherSelector(decryptCipherProviders, errorHandlingLayer);
    }

    public static void main(String[] args) {
        launch();
    }

}