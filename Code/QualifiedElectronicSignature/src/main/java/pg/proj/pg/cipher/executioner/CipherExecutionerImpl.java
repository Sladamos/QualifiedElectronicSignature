package pg.proj.pg.cipher.executioner;

import lombok.AllArgsConstructor;
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
        byte[] keyBytes = cipherInfo.keyInfo().keyContent();
        KeyGen keyGen = cipherInfo.keyGen();
        String algorithmType = cipherInfo.cipherType().getAlgorithmType();
        Cipher cipher = cipherInfo.cipher();
        Key key = keyGen.generateKey(keyBytes, algorithmType);
        try {
            cipher.init(cipherMode, key);
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
