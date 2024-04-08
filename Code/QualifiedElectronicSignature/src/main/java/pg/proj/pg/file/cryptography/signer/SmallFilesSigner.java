package pg.proj.pg.file.cryptography.signer;

import lombok.AllArgsConstructor;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.cryptography.container.FileSignerInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.signature.executioner.SignatureExecutioner;


@AllArgsConstructor
public class SmallFilesSigner implements FileSigner {

    private final FileContentOperator contentOperator;

    @Override
    public void signFile(FileSignerInformationContainer informationContainer) {
        SignatureExecutioner executioner = informationContainer.getSignatureExecutioner();
        DocumentInfo sourceInfo = informationContainer.getSourceFileInfo();
        FileInfo sourceFileInfo = sourceInfo.documentDetails().fileInfo();
        byte[] sourceContent = contentOperator.loadByteFileContent(sourceFileInfo);
        byte[] signedValue = executioner.sign(sourceContent);
        contentOperator.saveByteFileContent(informationContainer.getDestinationFileInfo(), signedValue); //TODO: xades
    }
}
