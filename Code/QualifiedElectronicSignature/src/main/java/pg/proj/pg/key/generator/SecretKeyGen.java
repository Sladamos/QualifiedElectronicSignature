package pg.proj.pg.key.generator;

import pg.proj.pg.error.CriticalAppError;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SecretKeyGen implements KeyGen {
    @Override
    public Key generateKey(byte[] bytes, String cipherType) {
        if(bytes == null || cipherType == null) {
            throw new CriticalAppError("Incorrect key gen arguments");
        }
        return new SecretKeySpec(bytes, cipherType);
    }
}
