package pg.proj.pg.key.generator;

import java.security.Key;
import java.security.PublicKey;

public interface PublicKeyGen {
    PublicKey generatePublicKey(byte[] bytes, String cipherType);
}
