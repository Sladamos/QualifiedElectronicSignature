package pg.proj.pg.cipher.executioner;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.generator.KeyGen;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@AllArgsConstructor
public class CipherExecutionerImpl implements CipherExecutioner {

    private final CipherInfo cipherInfo;

    @Override
    public byte[] encrypt(byte[] source) {
        launchCipherInMode(Cipher.ENCRYPT_MODE);
        Cipher cipher = cipherInfo.cipher();
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to encrypt content");
        }
    }

    @Override
    public byte[] decrypt(byte[] source) {
        launchCipherInMode(Cipher.DECRYPT_MODE);
        Cipher cipher = cipherInfo.cipher();
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to decrypt content");
        }
    }

    private void launchCipherInMode(int cipherMode) {
        String keyStr = cipherInfo.keyStr();
        byte[] keyBytes = Base64.getDecoder().decode(keyStr.getBytes());
        KeyGen keyGen = cipherInfo.keyGen();
        String cipherType = cipherInfo.cipherType();
        Cipher cipher = cipherInfo.cipher();
        Key key = keyGen.generateKey(keyBytes, cipherType);
        try {
            cipher.init(cipherMode, key);
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
