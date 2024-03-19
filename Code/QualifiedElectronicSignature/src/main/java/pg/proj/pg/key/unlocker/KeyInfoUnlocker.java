package pg.proj.pg.key.unlocker;

import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

public interface KeyInfoUnlocker {
    KeyInfo unlock(KeyInfo source, PasswordInfo passwordInfo);
}
