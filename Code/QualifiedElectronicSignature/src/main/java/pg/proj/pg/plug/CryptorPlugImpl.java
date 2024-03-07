package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private FileSelector fileSelector;

    private CipherSelector encryptCipherSelector;

    private CipherSelector decryptCipherSelector;

    @Override
    public void onEncryptCalled() {
        //FileProvider sourceFileProvider = fileSelector.selectFile(); //TODO add
        CipherProvider encryptCipherProvider = encryptCipherSelector.selectCipher();
        encryptCipherProvider.getCipher();
    }

    @Override
    public void onDecryptCalled() {
        //FileProvider sourceFileProvider = fileSelector.selectFile(); TODO add
        CipherProvider decryptCipherProvider = decryptCipherSelector.selectCipher();
    }
}
