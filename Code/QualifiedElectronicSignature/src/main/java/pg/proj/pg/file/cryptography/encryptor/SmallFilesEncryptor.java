package pg.proj.pg.file.cryptography.encryptor;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.file.cryptography.container.FileCryptoInformationContainer;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;

@AllArgsConstructor
public class SmallFilesEncryptor implements FileEncryptor {

    private final FileContentOperator contentOperator;

    @Override
    public void encryptFile(FileCryptoInformationContainer informationContainer) {
        FileInfo sourceFileInfo = informationContainer.getSourceFileInfo();
        byte[] content = contentOperator.loadByteFileContent(sourceFileInfo);
        CipherExecutioner cipherExecutioner = informationContainer.getCipherContainer();
        byte[] encryptedContent = cipherExecutioner.encrypt(content);
        FileInfo destinationFileInfo = informationContainer.getDestinationFileInfo();
        contentOperator.saveByteFileContent(destinationFileInfo, encryptedContent);
    }
}
