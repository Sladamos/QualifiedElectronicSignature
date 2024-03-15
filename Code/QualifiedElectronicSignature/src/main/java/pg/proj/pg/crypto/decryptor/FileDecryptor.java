package pg.proj.pg.crypto.decryptor;

import pg.proj.pg.crypto.container.CryptoInformationContainer;

public interface FileDecryptor {
    void decryptFile(CryptoInformationContainer informationContainer);
}
