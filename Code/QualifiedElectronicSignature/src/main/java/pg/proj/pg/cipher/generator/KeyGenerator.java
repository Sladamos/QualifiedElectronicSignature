package pg.proj.pg.cipher.generator;

import java.security.Key;

public interface KeyGenerator {
    Key generateKey(byte[] bytes, String cipherType);
}
