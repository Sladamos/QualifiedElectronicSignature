package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.key.generator.PrivateRsaKeyGen;
import pg.proj.pg.key.generator.PublicRsaKeyGen;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.provider.EncryptedCipherProvider;
import pg.proj.pg.cipher.provider.PlainCipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.cipher.selector.JavaFXCipherSelector;
import pg.proj.pg.crypto.decryptor.FileDecryptor;
import pg.proj.pg.crypto.decryptor.SmallFilesDecryptor;
import pg.proj.pg.crypto.encryptor.FileEncryptor;
import pg.proj.pg.crypto.encryptor.SmallFilesEncryptor;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.error.layer.ErrorHandlingLayerImpl;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.operator.SmallFilesContentOperator;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.file.selector.JavaFXFileSelector;
import pg.proj.pg.plug.CryptorPlug;
import pg.proj.pg.plug.CryptorPlugImpl;

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
        FileSelector encryptFileSelector = new JavaFXFileSelector(stage, "Select source file", Set.of(FileExtension.CPP, FileExtension.TXT));
        FileSelector decryptFileSelector = new JavaFXFileSelector(stage, "Select source file", Set.of(FileExtension.CYP));
        FileSelector cipherFileSelector = new JavaFXFileSelector(stage, "Select key file", Set.of(FileExtension.TXT));
        FileContentOperator cipherFileContentOperator = new SmallFilesContentOperator();
        CipherSelector encryptCipherSelector = createEncryptCipherSelector(errorHandlingLayer,
                cipherFileSelector, cipherFileContentOperator);
        CipherSelector decryptCipherSelector = createDecryptCipherSelector(errorHandlingLayer,
                cipherFileSelector, cipherFileContentOperator);
        FileEncryptor encryptor = createEncryptor();
        FileDecryptor decryptor = createDecryptor();
        return new CryptorPlugImpl(encryptFileSelector, decryptFileSelector, encryptCipherSelector,
                decryptCipherSelector, encryptor, decryptor);
    }

    private FileEncryptor createEncryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesEncryptor(contentOperator);
    }

    private FileDecryptor createDecryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesDecryptor(contentOperator);
    }

    private CipherSelector createEncryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGen rsaKeyGen = new PublicRsaKeyGen();
        List<CipherProvider> encryptCipherProviders = List.of(
                new EncryptedCipherProvider("RSA",
                        () -> CipherInfo.createFromFile(cipherFileSelector,
                                cipherFileContentOperator, rsaKeyGen, "RSA")));
        return new JavaFXCipherSelector(encryptCipherProviders, errorHandlingLayer);
    }

    private CipherSelector createDecryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGen rsaKeyGen = new PrivateRsaKeyGen();
        List<CipherProvider> decryptCipherProviders = List.of(
                new PlainCipherProvider("RSA",
                        () -> CipherInfo.createFromFile(cipherFileSelector,
                                cipherFileContentOperator, rsaKeyGen, "RSA")));
        return new JavaFXCipherSelector(decryptCipherProviders, errorHandlingLayer);
    }

    public static void main(String[] args) {
        launch();
    }

}