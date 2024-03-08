package pg.proj.pg.crypto.container;

import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.file.info.FileInfo;

public interface CryptoInformationContainer {

    FileInfo getSourceFileInfo();

    FileInfo getDestinationFileInfo();

    CipherExecutioner getCipherContainer();
}
