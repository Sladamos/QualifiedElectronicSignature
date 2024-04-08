package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.communicate.definition.Communicate;
import pg.proj.pg.communicate.receiver.api.CommunicateReceiver;
import pg.proj.pg.event.entity.api.OneArgEvent;
import pg.proj.pg.event.entity.impl.OneArgEventImpl;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainerImpl;
import pg.proj.pg.file.cryptography.decryptor.FileDecryptor;
import pg.proj.pg.file.cryptography.encryptor.FileEncryptor;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;
import pg.proj.pg.file.selector.FileSelector;

import java.nio.file.FileSystems;
import java.nio.file.Path;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private final FileSelector encryptFileSelector;

    private final FileSelector decryptFileSelector;

    private final CipherSelector encryptCipherSelector;

    private final CipherSelector decryptCipherSelector;

    private final FileEncryptor encryptor;

    private final FileDecryptor decryptor;

    private final OneArgEvent<Communicate> communicateEvent = new OneArgEventImpl<>();

    @Override
    public void onEncryptCalled() {
        sendCommunicate("Select file to encrypt");
        FileProvider sourceFileProvider = encryptFileSelector.selectFile();
        sendCommunicate("Select cipher");
        CipherProvider encryptCipherProvider = encryptCipherSelector.selectCipher();
        FileProvider destinationFileProvider = FileProviderImpl.fromSource(sourceFileProvider,
                FileExtension.CYP, "enc");
        FileCryptoInformationContainer informationContainer = new FileCryptoInformationContainerImpl(sourceFileProvider,
                destinationFileProvider, encryptCipherProvider);
        encryptor.encryptFile(informationContainer);
        sendCommunicate("File encrypted properly");
    }

    @Override
    public void onDecryptCalled() {
        sendCommunicate("Select file to decrypt");
        FileProvider sourceFileProvider = decryptFileSelector.selectFile();
        sendCommunicate("Select cipher");
        CipherProvider decryptCipherProvider = decryptCipherSelector.selectCipher();
        FileProvider destinationFileProvider = FileProviderImpl.fromSource(sourceFileProvider,
                FileExtension.TXT, "dec");
        FileCryptoInformationContainer informationContainer = new FileCryptoInformationContainerImpl(sourceFileProvider,
                destinationFileProvider, decryptCipherProvider);
        decryptor.decryptFile(informationContainer);
        sendCommunicate("File decrypted properly");
    }

    @Override
    public void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver) {
        communicateEvent.addListener(communicateReceiver::onCommunicateOccurred);
    }

    private void sendCommunicate(String content) {
        Communicate communicate = new Communicate(content);
        communicateEvent.invoke(communicate);
    }
}
