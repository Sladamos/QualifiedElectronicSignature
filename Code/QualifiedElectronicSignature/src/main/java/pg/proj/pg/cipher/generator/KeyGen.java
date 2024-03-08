package pg.proj.pg.cipher.generator;

import java.security.Key;

public interface KeyGen {
    Key generateKey(byte[] bytes, String cipherType);
}
