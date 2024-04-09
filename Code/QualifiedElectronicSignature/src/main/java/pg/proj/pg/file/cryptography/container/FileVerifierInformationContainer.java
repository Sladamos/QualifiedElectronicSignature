package pg.proj.pg.file.cryptography.container;

import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.signature.verifier.SignatureVerifier;

public interface FileVerifierInformationContainer {
    FileInfo getSourceFileInfo();
    DocumentInfo getSourceDocumentInfo();
    FileInfo getSignatureFileInfo();
    SignatureVerifier getSignatureVerifier();
}
