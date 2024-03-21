package pg.proj.pg.cipher.executioner;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.initializer.CipherInitializer;
import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.definition.BasicAppError;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@AllArgsConstructor
public class CipherExecutionerImpl implements CipherExecutioner {

    private final CipherInfo cipherInfo;

    private final CipherInitializer cipherInitializer;

    @Override
    public byte[] encrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.ENCRYPT_MODE);
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to encrypt content");
        }
    }

    @Override
    public byte[] decrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.DECRYPT_MODE);
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to decrypt content");
        }
    }
}
