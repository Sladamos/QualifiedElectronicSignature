package pg.proj.pg.cipher.initializer;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.key.generator.KeyGen;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;

@AllArgsConstructor
public class NonceCipherInitializer implements CipherInitializer {

    private final InitializationVector nonce;

    @Override
    public Cipher initializeCipher(CipherInfo cipherInfo, int cipherMode) {
        byte[] keyBytes = cipherInfo.keyInfo().keyContent();
        KeyGen keyGen = cipherInfo.keyGen();
        String algorithmType = cipherInfo.cipherType().getAlgorithmType();
        Cipher cipher = cipherInfo.cipher();
        Key key = keyGen.generateKey(keyBytes, algorithmType);
        try {
            cipher.init(cipherMode, key, new IvParameterSpec(nonce.iv()));
            return cipher;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
