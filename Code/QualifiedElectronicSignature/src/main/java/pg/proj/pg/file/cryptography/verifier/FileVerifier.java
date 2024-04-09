package pg.proj.pg.file.cryptography.verifier;

import pg.proj.pg.file.cryptography.container.FileVerifierInformationContainer;

public interface FileVerifier {
    boolean isFileSigned(FileVerifierInformationContainer informationContainer);
}
