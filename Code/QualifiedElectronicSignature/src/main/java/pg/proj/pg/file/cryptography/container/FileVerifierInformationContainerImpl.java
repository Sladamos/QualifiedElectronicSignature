package pg.proj.pg.file.cryptography.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.document.info.provider.DocumentInfoProvider;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.signature.provider.SignatureVerifierProvider;
import pg.proj.pg.signature.verifier.SignatureVerifier;

@AllArgsConstructor
public class FileVerifierInformationContainerImpl implements FileVerifierInformationContainer {

    private final FileProvider sourceFileProvider;

    private final FileProvider signatureFileProvider;

    private final DocumentInfoProvider documentInfoProvider;

    private final SignatureVerifierProvider verifierProvider;

    @Override
    public FileInfo getSourceFileInfo() {
        return sourceFileProvider.getFileInfo();
    }

    @Override
    public DocumentInfo getSourceDocumentInfo() {
        return documentInfoProvider.getDocumentInfo(sourceFileProvider.getFileInfo());
    }

    @Override
    public FileInfo getSignatureFileInfo() {
        return signatureFileProvider.getFileInfo();
    }

    @Override
    public SignatureVerifier getSignatureVerifier() {
        return verifierProvider.getSignatureVerifier();
    }
}
