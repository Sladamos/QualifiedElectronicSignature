package pg.proj.pg.key.generator;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SecretKeyGen implements KeyGen {
    @Override
    public Key generateKey(byte[] bytes, String cipherType) {
        return new SecretKeySpec(bytes, cipherType);
    }
}
