package pg.proj.pg.file.cryptography.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.document.info.provider.DocumentInfoProvider;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.signature.executioner.SignatureExecutioner;
import pg.proj.pg.signature.provider.SignatureExecutionerProvider;

@AllArgsConstructor
public class FileSignerInformationContainerImpl implements FileSignerInformationContainer {

    private final DocumentInfoProvider documentInfoProvider;

    private final FileProvider sourceFileInfoProvider;

    private final FileProvider destinationFileProvider;

    private final SignatureExecutionerProvider signatureExecutionerProvider;

    @Override
    public FileInfo getSourceFileInfo() {
        return sourceFileInfoProvider.getFileInfo();
    }

    @Override
    public DocumentInfo getSourceDocumentInfo() {
        return documentInfoProvider.getDocumentInfo(sourceFileInfoProvider.getFileInfo());
    }

    @Override
    public FileInfo getDestinationFileInfo() {
        return destinationFileProvider.getFileInfo();
    }

    @Override
    public SignatureExecutioner getSignatureExecutioner() {
        return signatureExecutionerProvider.getSignatureExecutioner();
    }
}
