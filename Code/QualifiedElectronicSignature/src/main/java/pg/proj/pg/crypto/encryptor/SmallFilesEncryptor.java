package pg.proj.pg.crypto.encryptor;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.crypto.container.CryptoInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;

@AllArgsConstructor
public class SmallFilesEncryptor implements FileEncryptor {

    private final FileContentOperator fileContentOperator;

    @Override
    public void encryptFile(CryptoInformationContainer informationContainer) {
        byte[] content = loadContent(informationContainer.getSourceFileInfo());
        CipherContainer cipherContainer = informationContainer.getCipherContainer();
        byte[] encryptedContent = cipherContainer.encrypt(content);
        fileContentOperator.saveByteFileContent(informationContainer.getDestinationFileInfo(), encryptedContent);
    }

    private byte[] loadContent(FileInfo fileInfo) {
        return fileContentOperator.loadStrFileContent(fileInfo).getBytes();
    }
}
