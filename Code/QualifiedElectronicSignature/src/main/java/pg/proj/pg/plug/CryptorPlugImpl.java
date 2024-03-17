package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainerImpl;
import pg.proj.pg.file.cryptography.decryptor.FileDecryptor;
import pg.proj.pg.file.cryptography.encryptor.FileEncryptor;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;
import pg.proj.pg.file.selector.FileSelector;

import java.nio.file.FileSystems;
import java.nio.file.Path;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private final FileSelector encryptFileSelector;

    private final FileSelector decryptFileSelector;

    private final CipherSelector encryptCipherSelector;

    private final CipherSelector decryptCipherSelector;

    private final FileEncryptor encryptor;

    private final FileDecryptor decryptor;

    @Override
    public void onEncryptCalled() {
        FileProvider sourceFileProvider = encryptFileSelector.selectFile();
        CipherProvider encryptCipherProvider = encryptCipherSelector.selectCipher();
        FileProvider destinationFileProvider = createFileProviderFromSource(sourceFileProvider, FileExtension.CYP, "enc");
        FileCryptoInformationContainer informationContainer = new FileCryptoInformationContainerImpl(sourceFileProvider,
                destinationFileProvider, encryptCipherProvider);
        encryptor.encryptFile(informationContainer);
    }

    @Override
    public void onDecryptCalled() {
        FileProvider sourceFileProvider = decryptFileSelector.selectFile();
        CipherProvider decryptCipherProvider = decryptCipherSelector.selectCipher();
        FileProvider destinationFileProvider = createFileProviderFromSource(sourceFileProvider, FileExtension.TXT, "dec");
        FileCryptoInformationContainer informationContainer = new FileCryptoInformationContainerImpl(sourceFileProvider,
                destinationFileProvider, decryptCipherProvider);
        decryptor.decryptFile(informationContainer);
        //TODO: add necessary communicates
    }

    private FileProvider createFileProviderFromSource(FileProvider sourceFileProvider, FileExtension extension,
                                                      String postfix) {
        FileInfo fileInfo = sourceFileProvider.getFileInfo();
        String newName = "%s_%s.%s".formatted(fileInfo.fileName().split("\\.")[0], postfix, extension.strValue());
        Path path = FileSystems.getDefault().getPath(fileInfo.canonicalPath());
        Path newPath = path.resolveSibling(newName);
        String newCanonicalPath = String.valueOf(newPath);
        FileInfo destinationFileInfo = new FileInfo(newCanonicalPath, newName, extension);
        return new FileProviderImpl(destinationFileInfo);
    }
}
