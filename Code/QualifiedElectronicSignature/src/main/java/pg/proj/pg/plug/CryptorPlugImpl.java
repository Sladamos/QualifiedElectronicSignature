package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.crypto.container.CryptoInformationContainer;
import pg.proj.pg.crypto.container.CryptoInformationContainerImpl;
import pg.proj.pg.crypto.encryptor.FileEncryptor;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import java.util.Arrays;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private FileSelector fileSelector;

    private CipherSelector encryptCipherSelector;

    private CipherSelector decryptCipherSelector;

    private FileEncryptor encryptor;

    @Override
    public void onEncryptCalled() {
        FileProvider sourceFileProvider = fileSelector.selectFile();
        CipherProvider encryptCipherProvider = encryptCipherSelector.selectCipher();
        CryptoInformationContainer informationContainer = new CryptoInformationContainerImpl(sourceFileProvider,
                sourceFileProvider, encryptCipherProvider);
        encryptor.encryptFile(informationContainer);
    }

    @Override
    public void onDecryptCalled() {
        FileProvider sourceFileProvider = fileSelector.selectFile();
        CipherProvider decryptCipherProvider = decryptCipherSelector.selectCipher();
        CryptoInformationContainer informationContainer = new CryptoInformationContainerImpl(sourceFileProvider,
                sourceFileProvider, decryptCipherProvider);
        //TODO: decryptor decrypt file
    }
}
