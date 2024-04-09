package pg.proj.pg.file.cryptography.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.signature.provider.SignatureInfoProvider;

@AllArgsConstructor
public class FileVerifierInformationContainerImpl implements FileVerifierInformationContainer {

    private final FileProvider sourceFileProvider;

    private final FileProvider signatureFileProvider;

    private final SignatureInfoProvider signatureInfoProvider;
}
