package pg.proj.pg.cipher.initializer;

import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.key.generator.KeyGen;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.Key;

public class SimpleCipherInitializer implements CipherInitializer {
    @Override
    public Cipher initializeCipher(CipherInfo cipherInfo, int cipherMode) {
        byte[] keyBytes = cipherInfo.keyInfo().keyContent();
        KeyGen keyGen = cipherInfo.keyGen();
        String algorithmType = cipherInfo.cipherType().getAlgorithmType();
        Cipher cipher = cipherInfo.cipher();
        Key key = keyGen.generateKey(keyBytes, algorithmType);
        try {
            cipher.init(cipherMode, key);
            return cipher;
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
