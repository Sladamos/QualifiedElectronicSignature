package pg.proj.pg.crypto.encryptor;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.crypto.container.CryptoInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;

@AllArgsConstructor
public class SmallFilesEncryptor implements FileEncryptor {

    private final FileContentOperator contentOperator;

    @Override
    public void encryptFile(CryptoInformationContainer informationContainer) {
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        byte[] content = contentOperator.loadByteFileContent(sourceFileInfo);
        CipherContainer cipherContainer = informationContainer.getCipherContainer();
        byte[] encryptedContent = cipherContainer.encrypt(content);
        FileInfo destinationFileInfo = informationContainer.getDestinationFileInfo();
        contentOperator.saveByteFileContent(destinationFileInfo, encryptedContent);
    }
}
