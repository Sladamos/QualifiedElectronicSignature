package pg.proj.pg.cipher.unlocker;

import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.password.info.PasswordInfo;

public interface CipherInfoUnlocker {
    CipherInfo unlock(CipherInfo source, PasswordInfo password);
}
