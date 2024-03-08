package pg.proj.pg.cipher.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.generator.KeyGenerator;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

@AllArgsConstructor
public class CipherContainerImpl implements CipherContainer {

    private final Cipher cipher;

    private final KeyGenerator keyGenerator;

    private final String keyStr;


    private final String cipherType;


    public static CipherContainerImpl createFromFile(FileSelector fileSelector, FileContentOperator fileContentOperator,
                                                     KeyGenerator keyGenerator, String cipherType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            var lines = fileContentOperator.loadStrFileContent(fileInfo).split("\n");
            String keyStr = String.join("", lines);
            Cipher cipher = Cipher.getInstance(cipherType);
            return new CipherContainerImpl(cipher, keyGenerator, keyStr, cipherType);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CriticalAppError("Unable to create cipher");
        }
    }

    @Override
    public byte[] encrypt(byte[] source) {
        launchCipherInMode(Cipher.ENCRYPT_MODE);
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to encrypt content");
        }
    }

    @Override
    public byte[] decrypt(byte[] source) {
        launchCipherInMode(Cipher.DECRYPT_MODE);
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to decrypt content");
        }
    }

    private void launchCipherInMode(int cipherMode) {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr.getBytes());
        Key key = keyGenerator.generateKey(keyBytes, cipherType);
        try {
            cipher.init(cipherMode, key);
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
