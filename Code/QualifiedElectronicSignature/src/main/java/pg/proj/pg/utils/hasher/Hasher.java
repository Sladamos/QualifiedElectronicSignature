package pg.proj.pg.utils.hasher;

import java.security.NoSuchAlgorithmException;

public interface Hasher {
    byte[] hashStr(String str);
}
