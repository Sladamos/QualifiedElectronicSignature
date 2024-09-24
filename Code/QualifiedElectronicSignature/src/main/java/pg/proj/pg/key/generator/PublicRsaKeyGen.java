package pg.proj.pg.key.generator;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class PublicRsaKeyGen implements KeyGen, PublicKeyGen {
    @Override
    public Key generateKey(byte[] bytes, String cipherType) {
        return generatePublicKey(bytes, cipherType);
    }

    @Override
    public PublicKey generatePublicKey(byte[] bytes, String cipherType) {
        if(bytes == null || cipherType == null) {
            throw new CriticalAppError("Incorrect key gen arguments");
        }
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(cipherType);
            return kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new BasicAppError("Unable to create public RSA key");
        }
    }
}
