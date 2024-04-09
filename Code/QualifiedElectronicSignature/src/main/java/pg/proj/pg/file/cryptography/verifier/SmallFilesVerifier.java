package pg.proj.pg.file.cryptography.verifier;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.cryptography.container.FileVerifierInformationContainer;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.xml.parser.XmlSignatureParser;

@AllArgsConstructor
public class SmallFilesVerifier implements FileVerifier {

    private final FileContentOperator contentOperator;

    private final XmlSignatureParser signatureParser;

    @Override
    public boolean isFileSigned(FileVerifierInformationContainer informationContainer) {

        return false;
    }
}
