package pg.proj.pg.file.cryptography.decryptor;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;

@AllArgsConstructor
public class SmallFilesDecryptor implements FileDecryptor {

    private final FileContentOperator contentOperator;

    @Override
    public void decryptFile(FileCryptoInformationContainer informationContainer) {
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        byte[] content = contentOperator.loadByteFileContent(sourceFileInfo);
        CipherExecutioner cipherExecutioner = informationContainer.getCipherContainer();
        byte[] decryptedContent = cipherExecutioner.decrypt(content);
        FileInfo destinationFileInfo = informationContainer.getDestinationFileInfo();
        contentOperator.saveByteFileContent(destinationFileInfo, decryptedContent);
    }
}
