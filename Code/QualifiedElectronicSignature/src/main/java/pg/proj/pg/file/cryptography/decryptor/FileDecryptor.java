package pg.proj.pg.file.cryptography.decryptor;

import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;

public interface FileDecryptor {
    void decryptFile(FileCryptoInformationContainer informationContainer);
}
