package pg.proj.pg.cipher.provider;

import pg.proj.pg.cipher.executioner.CipherExecutioner;

public interface CipherProvider {
    CipherExecutioner getCipher();
    String getUniqueName();
}
