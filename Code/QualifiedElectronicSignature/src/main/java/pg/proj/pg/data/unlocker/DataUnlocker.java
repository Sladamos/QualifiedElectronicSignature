package pg.proj.pg.data.unlocker;

import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.password.info.PasswordInfo;

public interface DataUnlocker {
    byte[] unlock(byte[] data, PasswordInfo passwordInfo, InitializationVector iv);
}
