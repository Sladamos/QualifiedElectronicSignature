package pg.proj.pg.key.generator;

import java.security.PrivateKey;

public interface PrivateKeyGen {
    PrivateKey getPrivateKey(byte[] bytes, String cipherType);
}
