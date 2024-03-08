package pg.proj.pg.crypto.container;

import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.file.info.FileInfo;

public interface CryptoInformationContainer {

    FileInfo getSourceFileInfo();

    FileInfo getDestinationFileInfo();

    CipherContainer getCipherContainer();
}
