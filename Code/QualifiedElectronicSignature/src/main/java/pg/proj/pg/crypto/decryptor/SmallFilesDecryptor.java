package pg.proj.pg.crypto.decryptor;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.crypto.container.CryptoInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;

@AllArgsConstructor
public class SmallFilesDecryptor implements FileDecryptor {

    private final FileContentOperator contentOperator;

    @Override
    public void decryptFile(CryptoInformationContainer informationContainer) {
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        byte[] content = contentOperator.loadByteFileContent(sourceFileInfo);
        CipherContainer cipherContainer = informationContainer.getCipherContainer();
        byte[] decryptedContent = cipherContainer.decrypt(content);
        FileInfo destinationFileInfo = informationContainer.getDestinationFileInfo();
        contentOperator.saveByteFileContent(destinationFileInfo, decryptedContent);
    }
}
