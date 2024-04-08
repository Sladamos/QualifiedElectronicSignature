package pg.proj.pg.key.generator;

import pg.proj.pg.error.definition.BasicAppError;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateRsaKeyGen implements KeyGen, PrivateKeyGen {
    @Override
    public Key generateKey(byte[] bytes, String cipherType) {
        return getPrivateKey(bytes, cipherType);
    }

    @Override
    public PrivateKey getPrivateKey(byte[] bytes, String cipherType) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(cipherType);
            return kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new BasicAppError("Unable to create private RSA key");
        }
    }
}
