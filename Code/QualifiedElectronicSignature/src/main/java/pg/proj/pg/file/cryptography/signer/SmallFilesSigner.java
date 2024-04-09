package pg.proj.pg.file.cryptography.signer;

import lombok.AllArgsConstructor;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.cryptography.container.FileSignerInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.signature.executioner.SignatureExecutioner;
import pg.proj.pg.signature.info.SignatureInfo;
import pg.proj.pg.xml.writer.SignatureXmlWriter;


@AllArgsConstructor
public class SmallFilesSigner implements FileSigner {

    private final FileContentOperator contentOperator;

    private final SignatureXmlWriter xmlWriter;

    @Override
    public void signFile(FileSignerInformationContainer informationContainer) {
        SignatureExecutioner executioner = informationContainer.getSignatureExecutioner();
        DocumentInfo documentInfo = informationContainer.getSourceDocumentInfo();
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        byte[] sourceContent = contentOperator.loadByteFileContent(sourceFileInfo);
        byte[] signedValue = executioner.sign(sourceContent);
        SignatureInfo xmlInfo = new SignatureInfo(documentInfo, signedValue);
        String xml = xmlWriter.toXml(xmlInfo);
        contentOperator.saveStrFileContent(informationContainer.getDestinationFileInfo(), xml);
    }
}
