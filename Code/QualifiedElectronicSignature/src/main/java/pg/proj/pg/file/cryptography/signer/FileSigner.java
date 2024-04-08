package pg.proj.pg.file.cryptography.signer;

import pg.proj.pg.file.cryptography.container.FileSignerInformationContainer;

public interface FileSigner {
    void signFile(FileSignerInformationContainer informationContainer);
}
