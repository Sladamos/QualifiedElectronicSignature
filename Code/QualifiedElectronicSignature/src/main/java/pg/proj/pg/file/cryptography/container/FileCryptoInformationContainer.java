package pg.proj.pg.file.cryptography.container;

import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.file.info.FileInfo;

public interface FileCryptoInformationContainer {

    FileInfo getSourceFileInfo();

    FileInfo getDestinationFileInfo();

    CipherExecutioner getCipherContainer();
}
