package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pg.proj.pg.author.provider.HardcodedAuthorProvider;
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
import pg.proj.pg.date.provider.CurrentDateProvider;
import pg.proj.pg.document.details.provider.DocumentDetailsProviderImpl;
import pg.proj.pg.document.info.provider.DocumentInfoProvider;
import pg.proj.pg.document.info.provider.DocumentInfoProviderImpl;
import pg.proj.pg.file.cryptography.signer.FileSigner;
import pg.proj.pg.file.cryptography.signer.SmallFilesSigner;
import pg.proj.pg.file.cryptography.verifier.FileVerifier;
import pg.proj.pg.file.cryptography.verifier.SmallFilesVerifier;
import pg.proj.pg.file.detector.DesktopFileDetector;
import pg.proj.pg.file.detector.FileDetector;
import pg.proj.pg.file.detector.UsbFileDetector;
import pg.proj.pg.file.selector.PreDetectedFileSelector;
import pg.proj.pg.key.generator.*;
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
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.key.unlocker.RsaKeyInfoUnlocker;
import pg.proj.pg.password.selector.JavaFXPasswordSelector;
import pg.proj.pg.plug.CryptorPlug;
import pg.proj.pg.plug.CryptorPlugImpl;
import pg.proj.pg.plug.SignerPlug;
import pg.proj.pg.plug.SignerPlugImpl;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;
import pg.proj.pg.signature.initializer.SignatureExecutionerInitializer;
import pg.proj.pg.signature.initializer.SignatureExecutionerInitializerImpl;
import pg.proj.pg.signature.provider.EncryptedSignatureExecutionerProvider;
import pg.proj.pg.signature.provider.SignatureExecutionerProvider;
import pg.proj.pg.signature.provider.SignatureInfoProvider;
import pg.proj.pg.signature.provider.XmlSignatureInfoProvider;
import pg.proj.pg.signature.selector.JavaFXSignatureExecutionerSelector;
import pg.proj.pg.signature.selector.SignatureExecutionerSelector;
import pg.proj.pg.signature.type.SignatureType;
import pg.proj.pg.signature.unlocker.SignatureExecutionerInfoUnlocker;
import pg.proj.pg.signature.unlocker.SignatureExecutionerInfoUnlockerImpl;
import pg.proj.pg.xml.parser.XadesXmlSignatureParser;
import pg.proj.pg.xml.parser.XmlSignatureParser;
import pg.proj.pg.xml.writer.SignatureXmlWriter;
import pg.proj.pg.xml.writer.XadesSignatureXmlWriter;

import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class MainApplication extends Application {

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
        SignerPlug signerPlug = createSignerPlug(stage, errorHandlingLayer, receiver);
        controller.setErrorHandlingLayer(errorHandlingLayer);
        controller.setCryptorPlug(cryptorPlug);
        controller.setSignerPlug(signerPlug);
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
        Set<FileExtension> encryptorFileExtensions = createExtensionsPossibleToEncrypt();
        FileSelector encryptFileSelector = new JavaFXFileSelector(stage, "Select source file", encryptorFileExtensions);
        FileSelector decryptFileSelector = new JavaFXFileSelector(stage, "Select source file", Set.of(FileExtension.CYP));
        FileContentOperator cipherFileContentOperator = new SmallFilesContentOperator();
        CipherSelector encryptCipherSelector = createEncryptCipherSelector(stage,
                cipherFileContentOperator, errorHandlingLayer);
        CipherSelector decryptCipherSelector = createDecryptCipherSelector(stage,
                cipherFileContentOperator, errorHandlingLayer);
        FileEncryptor encryptor = createEncryptor();
        FileDecryptor decryptor = createDecryptor();
        CryptorPlug cryptorPlug = new CryptorPlugImpl(encryptFileSelector, decryptFileSelector, encryptCipherSelector,
                decryptCipherSelector, encryptor, decryptor);
        cryptorPlug.registerCommunicatesReceiver(receiver);
        return cryptorPlug;
    }

    private Set<FileExtension> createExtensionsPossibleToEncrypt() {
        Set<FileExtension> fileExtensions = new LinkedHashSet<>();
        fileExtensions.add(FileExtension.CPP);
        fileExtensions.add(FileExtension.TXT);
        return fileExtensions;
    }

    private FileEncryptor createEncryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesEncryptor(contentOperator);
    }

    private FileDecryptor createDecryptor() {
        FileContentOperator contentOperator = new SmallFilesContentOperator();
        return new SmallFilesDecryptor(contentOperator);
    }

    private CipherSelector createDecryptCipherSelector(Stage stage,
                                                       FileContentOperator cipherFileContentOperator,
                                                       ErrorHandlingLayer errorHandlingLayer) {
        CipherProvider encryptedRsaProvider = createEncryptedRsaPrivateKeyCipherProvider(stage,
                cipherFileContentOperator, errorHandlingLayer);
        List<CipherProvider> encryptCipherProviders = List.of(encryptedRsaProvider);
        return new JavaFXCipherSelector(encryptCipherProviders, errorHandlingLayer);
    }

    private CipherSelector createEncryptCipherSelector(Stage stage,
                                                       FileContentOperator cipherFileContentOperator,
                                                       ErrorHandlingLayer errorHandlingLayer) {
        CipherProvider plainRsaProvider = createPlainRsaPublicKeyCipherProvider(stage, cipherFileContentOperator);
        List<CipherProvider> decryptCipherProviders = List.of(plainRsaProvider);
        return new JavaFXCipherSelector(decryptCipherProviders, errorHandlingLayer);
    }

    private CipherProvider createPlainRsaPublicKeyCipherProvider(Stage stage,
                                                                 FileContentOperator cipherFileContentOperator) {
        FileSelector publicKeySelector = new JavaFXFileSelector(stage,
                "Select plain public key", Set.of(FileExtension.PUK));
        FileDetector publicKeyDetector = new DesktopFileDetector("public_key", FileExtension.PUK);
        FileSelector publicKeyPreDetectedFileSelector = new PreDetectedFileSelector(publicKeySelector, publicKeyDetector);
        KeyGen rsaKeyGen = new PublicRsaKeyGen();
        Supplier<CipherInfo> cipherInfoSupplier = () -> CipherInfo.createFromPEMFile(publicKeyPreDetectedFileSelector,
                cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        CipherInitializer rsaCipherInitializer = new SimpleCipherInitializer();
        return new PlainCipherProvider("RSA", rsaCipherInitializer, cipherInfoSupplier);
    }

    private CipherProvider createPlainRsaPrivateKeyCipherProvider(Stage stage,
                                                                  FileContentOperator cipherFileContentOperator) {
        CipherInitializer rsaCipherInitializer = new SimpleCipherInitializer();
        KeyGen rsaKeyGen = new PrivateRsaKeyGen();
        FileSelector plainPrivateKeySelector = new JavaFXFileSelector(stage,
                "Select plain private key", Set.of(FileExtension.PPK));
        FileDetector plainPrivateKeyDetector = new DesktopFileDetector("private_key", FileExtension.PPK);
        FileSelector plainCipherPreDetectedFileSelector = new PreDetectedFileSelector(plainPrivateKeySelector, plainPrivateKeyDetector);
        Supplier<CipherInfo> plainCipherInfoSupplier = () -> CipherInfo.createFromPEMFile(plainCipherPreDetectedFileSelector,
                cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        return new PlainCipherProvider("PlainRSA", rsaCipherInitializer, plainCipherInfoSupplier);
    }

    private CipherProvider createEncryptedRsaPrivateKeyCipherProvider(Stage stage,
                                                                      FileContentOperator cipherFileContentOperator,
                                                                      ErrorHandlingLayer errorHandlingLayer) {
        CipherInitializer rsaCipherInitializer = new SimpleCipherInitializer();
        FileSelector encryptedPrivateKeySelector = new JavaFXFileSelector(stage,
                "Select encrypted private key", Set.of(FileExtension.EPK));
        FileDetector encryptedPrivateKeyDetector = new UsbFileDetector("private_key", FileExtension.EPK);
        FileSelector encryptedCipherPreDetectedFileSelector = new PreDetectedFileSelector(encryptedPrivateKeySelector, encryptedPrivateKeyDetector);
        KeyGen rsaKeyGen = new PrivateRsaKeyGen();
        JavaFXPasswordSelector passwordSelector = new JavaFXPasswordSelector(errorHandlingLayer);
        CipherInfoUnlocker unlocker = createCipherInfoUnlocker();
        Supplier<CipherInfo> encryptedCipherInfoSupplier = () ->
                CipherInfo.createFromBinaryFile(encryptedCipherPreDetectedFileSelector
                        , cipherFileContentOperator, rsaKeyGen, CipherType.RSA);
        return new EncryptedCipherProvider("EncRSA", unlocker, passwordSelector,
                rsaCipherInitializer, encryptedCipherInfoSupplier);
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

    private SignerPlug createSignerPlug(Stage stage, ErrorHandlingLayer errorHandlingLayer, MainReceiver receiver) {
        Set<FileExtension> signerFileExtensions = createExtensionsPossibleToEncrypt();
        FileSelector signFileSelector = new JavaFXFileSelector(stage, "Select file to sign", signerFileExtensions);
        FileSelector verifyFileSelector = new JavaFXFileSelector(stage, "Select file to verify", signerFileExtensions);
        FileSelector signatureFileSelector = new JavaFXFileSelector(stage, "Select file with signature", Set.of(FileExtension.XML));
        FileContentOperator signerFileContentOperator = new SmallFilesContentOperator();
        SignatureExecutionerSelector encryptExecutionerSelector = createEncryptExecutionerSelector(stage,
                signerFileContentOperator, errorHandlingLayer);
        FileSigner fileSigner = createFileSigner();
        FileVerifier fileVerifier = createFileVerifier();
        SignerPlug signerPlug = new SignerPlugImpl(signFileSelector, verifyFileSelector, signatureFileSelector,
                encryptExecutionerSelector, fileSigner, fileVerifier,
                this::createDocumentInfoProvider, this::createDocumentInfoProvider);
        signerPlug.registerCommunicatesReceiver(receiver);
        return signerPlug;
    }

    private SignatureExecutionerSelector createEncryptExecutionerSelector(Stage stage,
            FileContentOperator contentOperator, ErrorHandlingLayer errorHandlingLayer) {
        SignatureExecutionerProvider encryptedRsaProvider = createEncryptedRsaPrivateKeyExecutionerProvider(stage,
                contentOperator, errorHandlingLayer);
        List<SignatureExecutionerProvider> encryptExecutionerProviders = List.of(encryptedRsaProvider);
        return new JavaFXSignatureExecutionerSelector(encryptExecutionerProviders, errorHandlingLayer);
    }

    private SignatureExecutionerProvider createEncryptedRsaPrivateKeyExecutionerProvider(Stage stage,
                                                                                         FileContentOperator contentOperator,
                                                                                         ErrorHandlingLayer errorHandlingLayer) {
        SignatureExecutionerInitializer rsaExecutionerInitializer = new SignatureExecutionerInitializerImpl();
        FileSelector encryptedPrivateKeySelector = new JavaFXFileSelector(stage,
                "Select encrypted private key", Set.of(FileExtension.EPK));
        FileDetector encryptedPrivateKeyDetector = new DesktopFileDetector("private_key", FileExtension.EPK); //TODO swtich to usb
        FileSelector encryptedCipherPreDetectedFileSelector = new PreDetectedFileSelector(encryptedPrivateKeySelector, encryptedPrivateKeyDetector);
        PrivateKeyGen rsaKeyGen = new PrivateRsaKeyGen();
        JavaFXPasswordSelector passwordSelector = new JavaFXPasswordSelector(errorHandlingLayer);
        SignatureExecutionerInfoUnlocker unlocker = createExecutionerInfoUnlocker();
        Supplier<SignatureExecutionerInfo> encryptedSignatureInfoSupplier = () ->
                SignatureExecutionerInfo.createFromBinaryFile(encryptedCipherPreDetectedFileSelector
                        ,contentOperator, rsaKeyGen, SignatureType.RSA);
        return new EncryptedSignatureExecutionerProvider("EncRSA", unlocker, passwordSelector,
                rsaExecutionerInitializer, encryptedSignatureInfoSupplier);
    }

    private SignatureExecutionerInfoUnlocker createExecutionerInfoUnlocker() {
        Hasher hasher = new Sha256Hasher();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, this::createAesDecryptor);
        KeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        return new SignatureExecutionerInfoUnlockerImpl(keyInfoUnlocker);
    }

    private FileSigner createFileSigner() {
        FileContentOperator signerFileContentOperator = new SmallFilesContentOperator();
        SignatureXmlWriter signatureXmlWriter = new XadesSignatureXmlWriter();
        return new SmallFilesSigner(signerFileContentOperator, signatureXmlWriter);
    }

    private FileVerifier createFileVerifier() {
        FileContentOperator verifierFileContentOperator = new SmallFilesContentOperator();
        FileContentOperator signatureProviderContentOperator = new SmallFilesContentOperator();
        XmlSignatureParser signatureParser = new XadesXmlSignatureParser();
        SignatureInfoProvider signatureInfoProvider = new XmlSignatureInfoProvider(signatureParser,
                signatureProviderContentOperator);
        return new SmallFilesVerifier(verifierFileContentOperator, signatureInfoProvider);
    }

    private DocumentInfoProvider createDocumentInfoProvider() {
        return new DocumentInfoProviderImpl(new DocumentDetailsProviderImpl(),
                new HardcodedAuthorProvider(), new CurrentDateProvider());
    }


    public static void main(String[] args) {
        launch();
    }

}