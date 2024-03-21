package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.initializer.CipherInitializer;
import pg.proj.pg.cipher.initializer.NonceCipherInitializer;
import pg.proj.pg.cipher.initializer.SimpleCipherInitializer;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.cipher.unlocker.CipherInfoUnlocker;
import pg.proj.pg.cipher.unlocker.CipherInfoUnlockerImpl;
import pg.proj.pg.data.hasher.Hasher;
import pg.proj.pg.data.hasher.Sha256Hasher;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.data.unlocker.HashedDataUnlocker;
import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.key.generator.PrivateRsaKeyGen;
import pg.proj.pg.key.generator.PublicRsaKeyGen;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.provider.EncryptedCipherProvider;
import pg.proj.pg.cipher.provider.PlainCipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.cipher.selector.JavaFXCipherSelector;
import pg.proj.pg.file.cryptography.decryptor.FileDecryptor;
import pg.proj.pg.file.cryptography.decryptor.SmallFilesDecryptor;
import pg.proj.pg.file.cryptography.encryptor.FileEncryptor;
import pg.proj.pg.file.cryptography.encryptor.SmallFilesEncryptor;
import pg.proj.pg.error.layer.ErrorHandlingLayer;
import pg.proj.pg.error.layer.ErrorHandlingLayerImpl;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.operator.SmallFilesContentOperator;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.file.selector.JavaFXFileSelector;
import pg.proj.pg.key.generator.SecretKeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.key.unlocker.RsaKeyInfoUnlocker;
import pg.proj.pg.password.selector.JavaFXPasswordSelector;
import pg.proj.pg.plug.CryptorPlug;
import pg.proj.pg.plug.CryptorPlugImpl;

import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class MainApplication extends Application {

    //TODO: add communicates

    private static final byte[] hardcodedNonce =
            new byte[]{'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 512, 384);
        stage.setTitle("Emulator");
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        initializeVariables(controller, stage);
        stage.show();
    }

    private void initializeVariables(MainController controller, Stage stage) {
        MainReceiver receiver = createMainReceiver(controller);
        ErrorHandlingLayer errorHandlingLayer = createErrorHandlingLayer(receiver);
        CryptorPlug cryptorPlug = createCryptorPlug(stage, errorHandlingLayer, receiver);
        controller.setErrorHandlingLayer(errorHandlingLayer);
        controller.setCryptorPlug(cryptorPlug);
    }

    private MainReceiver createMainReceiver(MainController controller) {
        return new MainReceiver(controller::onErrorOccurred,
                controller::onCommunicateOccurred, controller::onExitCalled );
    }

    private ErrorHandlingLayer createErrorHandlingLayer(MainReceiver receiver) {
        ErrorHandlingLayer errorHandlingLayer = new ErrorHandlingLayerImpl();
        errorHandlingLayer.registerBasicErrorReceiver(receiver);
        errorHandlingLayer.registerCriticalErrorReceiver(receiver);
        return errorHandlingLayer;
    }

    private CryptorPlug createCryptorPlug(Stage stage, ErrorHandlingLayer errorHandlingLayer, MainReceiver receiver) {
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
        CryptorPlug cryptorPlug = new CryptorPlugImpl(encryptFileSelector, decryptFileSelector, encryptCipherSelector,
                decryptCipherSelector, encryptor, decryptor);
        cryptorPlug.registerCommunicatesReceiver(receiver);
        return cryptorPlug;
    }

    private FileEncryptor createEncryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesEncryptor(contentOperator);
    }

    private FileDecryptor createDecryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesDecryptor(contentOperator);
    }

    private CipherSelector createDecryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGen rsaKeyGen = new PrivateRsaKeyGen();
        JavaFXPasswordSelector passwordSelector = new JavaFXPasswordSelector(errorHandlingLayer);
        CipherInfoUnlocker unlocker = createCipherInfoUnlocker();
        CipherInitializer rsaCipherInitializer = new SimpleCipherInitializer();

        Supplier<CipherInfo> encryptedCipherInfoSupplier = () -> CipherInfo.createFromBinaryFile(cipherFileSelector,
                cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        CipherProvider encryptedRsaProvider = new EncryptedCipherProvider("EncRSA",
                unlocker, passwordSelector, rsaCipherInitializer, encryptedCipherInfoSupplier);

        Supplier<CipherInfo> rawCipherInfoSupplier = () -> CipherInfo.createFromPEMFile(cipherFileSelector,
                cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        CipherProvider rawRsaProvider = new PlainCipherProvider("PlainRSA",
                rsaCipherInitializer, rawCipherInfoSupplier);

        List<CipherProvider> encryptCipherProviders = List.of(encryptedRsaProvider, rawRsaProvider);
        return new JavaFXCipherSelector(encryptCipherProviders, errorHandlingLayer);
    }

    private CipherSelector createEncryptCipherSelector(ErrorHandlingLayer errorHandlingLayer,
                                                       FileSelector cipherFileSelector,
                                                       FileContentOperator cipherFileContentOperator) {
        KeyGen rsaKeyGen = new PublicRsaKeyGen();
        Supplier<CipherInfo> cipherInfoSupplier = () -> CipherInfo.createFromPEMFile(cipherFileSelector,
                cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        CipherInitializer rsaCipherInitializer = new SimpleCipherInitializer();
        CipherProvider plainRsaProvider = new PlainCipherProvider("RSA",
                rsaCipherInitializer, cipherInfoSupplier);
        List<CipherProvider> decryptCipherProviders = List.of(plainRsaProvider);
        return new JavaFXCipherSelector(decryptCipherProviders, errorHandlingLayer);
    }

    private CipherInfoUnlocker createCipherInfoUnlocker() {
        Hasher hasher = new Sha256Hasher();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, this::createAesDecryptor);
        KeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        return new CipherInfoUnlockerImpl(keyInfoUnlocker);
    }

    private CipherExecutioner createAesDecryptor(KeyInfo keyInfo)  {
        CipherType cipherType = CipherType.AES;
        KeyGen keyGen = new SecretKeyGen();
        CipherInfo cipherInfo = CipherInfo.createFromProvidedKey(keyGen, keyInfo, cipherType);
        CipherInitializer aesCipherInitializer = new NonceCipherInitializer(new IvParameterSpec(hardcodedNonce));
        return new CipherExecutionerImpl(cipherInfo, aesCipherInitializer);
    }

    public static void main(String[] args) {
        launch();
    }

}