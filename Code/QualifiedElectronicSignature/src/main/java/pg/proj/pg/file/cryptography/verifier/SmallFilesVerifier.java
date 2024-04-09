package pg.proj.pg.file.cryptography.verifier;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.cryptography.container.FileVerifierInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.signature.info.SignatureInfo;
import pg.proj.pg.signature.provider.SignatureInfoProvider;
import pg.proj.pg.signature.verifier.SignatureVerifier;

@AllArgsConstructor
public class SmallFilesVerifier implements FileVerifier {

    private final FileContentOperator contentOperator;

    private final SignatureInfoProvider signatureInfoProvider;

    @Override
    public boolean isFileSigned(FileVerifierInformationContainer informationContainer) {
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        FileInfo signatureFileInfo = informationContainer.getSignatureFileInfo();
        SignatureInfo signatureInfo = signatureInfoProvider.getSignatureInfo(signatureFileInfo);
        SignatureVerifier verifier = informationContainer.getSignatureVerifier();
        byte[] sourceFileContent = contentOperator.loadByteFileContent(sourceFileInfo);
        boolean isSignatureValid = verifier.isSignatureValid(sourceFileContent, signatureInfo.signedValue());
        //TODO verify attributes
        return isSignatureValid;
    }
}
