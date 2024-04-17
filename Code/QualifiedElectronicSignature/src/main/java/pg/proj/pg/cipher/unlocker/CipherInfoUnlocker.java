package pg.proj.pg.cipher.unlocker;

import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.info.EncryptedCipherInfo;
import pg.proj.pg.password.info.PasswordInfo;

public interface CipherInfoUnlocker {
    CipherInfo unlock(EncryptedCipherInfo encryptedSource, PasswordInfo password);
}
