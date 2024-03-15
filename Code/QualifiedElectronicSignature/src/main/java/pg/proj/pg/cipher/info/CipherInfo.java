package pg.proj.pg.cipher.info;

import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.info.KeyInfoImpl;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public record CipherInfo(Cipher cipher, KeyGen keyGen, KeyInfo keyInfo, String cipherType) {

    public static CipherInfo createFromFile(FileSelector fileSelector, FileContentOperator fileContentOperator, KeyGen keyGen, String cipherType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            String keyStr = fileContentOperator.loadStrFileContent(fileInfo)
                    .replace(System.lineSeparator(), "");
            KeyInfo keyInfo = new KeyInfoImpl(keyStr.getBytes());
            Cipher cipher = Cipher.getInstance(cipherType);
            return new CipherInfo(cipher, keyGen, keyInfo, cipherType);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CriticalAppError("Unable to get all necessary cipher information");
        }
    }
}
