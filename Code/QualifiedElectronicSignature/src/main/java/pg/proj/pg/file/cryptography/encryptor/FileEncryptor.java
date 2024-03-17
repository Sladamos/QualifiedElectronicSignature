package pg.proj.pg.file.cryptography.encryptor;

import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;

public interface FileEncryptor {
    void encryptFile(FileCryptoInformationContainer informationContainer);
}
