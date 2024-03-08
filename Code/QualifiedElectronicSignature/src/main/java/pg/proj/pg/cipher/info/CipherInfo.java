package pg.proj.pg.cipher.info;

import pg.proj.pg.cipher.generator.KeyGen;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public record CipherInfo(Cipher cipher, KeyGen keyGen, String keyStr, String cipherType) {

    public static CipherInfo createFromFile(FileSelector fileSelector, FileContentOperator fileContentOperator, KeyGen keyGen, String cipherType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            var lines = fileContentOperator.loadStrFileContent(fileInfo).split("\n");
            String keyStr = String.join("", lines);
            Cipher cipher = Cipher.getInstance(cipherType);
            return new CipherInfo(cipher, keyGen, keyStr, cipherType);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CriticalAppError("Unable to get all necessary cipher information");
        }
    }
}
