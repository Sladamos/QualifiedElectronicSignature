package pg.proj.pg.crypto.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;

@AllArgsConstructor
public class CryptoInformationContainerImpl implements CryptoInformationContainer {

    private final FileProvider sourceFileProvider;

    private final FileProvider destinationFileProvider;

    private final CipherProvider cipherProvider;

    @Override
    public FileInfo getDestinationFileInfo() {
        return destinationFileProvider.getFileInfo();
    }

    @Override
    public FileInfo getSourceFileInfo() {
        return sourceFileProvider.getFileInfo();
    }

    @Override
    public CipherExecutioner getCipherContainer() {
        return cipherProvider.getCipher();
    }
}
