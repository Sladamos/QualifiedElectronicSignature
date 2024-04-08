package pg.proj.pg.file.cryptography.container;

import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.signature.executioner.SignatureExecutioner;

public interface FileSignerInformationContainer {

    DocumentInfo getSourceFileInfo();

    FileInfo getDestinationFileInfo();

    SignatureExecutioner getSignatureExecutioner();
}
