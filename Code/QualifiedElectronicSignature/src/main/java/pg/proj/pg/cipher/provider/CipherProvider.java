package pg.proj.pg.cipher.provider;

import pg.proj.pg.cipher.container.CipherContainer;

public interface CipherProvider {
    CipherContainer getCipher();
    String getUniqueName();
}
