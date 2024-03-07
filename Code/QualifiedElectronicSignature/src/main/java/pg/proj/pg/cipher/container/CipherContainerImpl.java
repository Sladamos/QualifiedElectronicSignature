package pg.proj.pg.cipher.container;

import lombok.AllArgsConstructor;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
public class CipherContainerImpl implements CipherContainer {

    private final Cipher cipher;

    private final String key;

    public static CipherContainerImpl createFromFile(FileSelector fileSelector, FileOperator fileOperator, String cipherInstance) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            var lines = fileOperator.loadFileContent(fileInfo).split("\n");
            String key = String.join("", lines);
            Cipher cipher = Cipher.getInstance(cipherInstance);
            return new CipherContainerImpl(cipher, key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CriticalAppError("Unable to create cipher");
        }
    }

    @Override
    public byte[] encrypt(byte[] source) {
        //TODO initialize cipher
        return cipherMethod(source);
    }

    @Override
    public byte[] decrypt(byte[] source) {
        //initialize cipher
        return cipherMethod(source);
    }

    private byte[] cipherMethod(byte[] source) {
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to cipher!");
        }
    }
}
