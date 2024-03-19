package pg.proj.pg.key.generator;

import java.security.Key;

public interface KeyGen {
    Key generateKey(byte[] bytes, String cipherType);
}
