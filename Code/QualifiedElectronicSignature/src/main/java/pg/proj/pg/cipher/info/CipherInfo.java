package pg.proj.pg.cipher.info;

import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.key.info.KeyInfo;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public record CipherInfo(Cipher cipher, KeyGen keyGen, KeyInfo keyInfo, CipherType cipherType) {

    public static CipherInfo createFromBinaryFile(FileSelector fileSelector,
                                                  FileContentOperator fileContentOperator,
                                                  KeyGen keyGen, CipherType cipherType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            byte[] keyBytes = fileContentOperator.loadByteFileContent(fileInfo);
            KeyInfo keyInfo = new KeyInfo(keyBytes);
            Cipher cipher = Cipher.getInstance(cipherType.getStrValue());
            return new CipherInfo(cipher, keyGen, keyInfo, cipherType);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new BasicAppError("Unable to get all necessary cipher information");
        }
    }

    public static CipherInfo createFromPEMFile(FileSelector fileSelector,
                                            FileContentOperator fileContentOperator,
                                            KeyGen keyGen, CipherType cipherType) {
        try {
            CipherInfo cipherInfo = createFromBinaryFile(fileSelector, fileContentOperator, keyGen, cipherType);
            byte[] decodedKeyBytes = Base64.getDecoder().decode(cipherInfo.keyInfo.keyContent());
            KeyInfo keyInfo = new KeyInfo(decodedKeyBytes);
            return new CipherInfo(cipherInfo.cipher, cipherInfo.keyGen, keyInfo, cipherInfo.cipherType);
        } catch (IllegalArgumentException e) {
            throw new BasicAppError("Unable to get all necessary cipher information");
        }
    }

    public static CipherInfo createFromProvidedKey(KeyGen keyGen, KeyInfo keyInfo, CipherType cipherType) {
        try {
            Cipher cipher = Cipher.getInstance(cipherType.getStrValue());
            return new CipherInfo(cipher, keyGen, keyInfo, cipherType);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CriticalAppError("Unable to get all necessary cipher information");
        }
    }
}
