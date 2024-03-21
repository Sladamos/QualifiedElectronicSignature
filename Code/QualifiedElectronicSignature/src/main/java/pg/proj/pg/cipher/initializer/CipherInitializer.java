package pg.proj.pg.cipher.initializer;

import pg.proj.pg.cipher.info.CipherInfo;

import javax.crypto.Cipher;

public interface CipherInitializer {
    Cipher initializeCipher(CipherInfo cipherInfo, int cipherMode);
}
