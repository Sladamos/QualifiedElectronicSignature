package pg.proj.pg.cipher.selector;

import pg.proj.pg.cipher.provider.CipherProvider;

public interface CipherSelector {
    CipherProvider selectCipher();
}
