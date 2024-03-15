package pg.proj.pg.crypto.encryptor;

import pg.proj.pg.crypto.container.CryptoInformationContainer;

public interface FileEncryptor {
    void encryptFile(CryptoInformationContainer informationContainer);
}
