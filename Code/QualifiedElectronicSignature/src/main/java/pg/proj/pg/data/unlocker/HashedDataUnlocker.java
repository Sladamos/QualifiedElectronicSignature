package pg.proj.pg.data.unlocker;

import pg.proj.pg.password.info.PasswordInfo;

public class HashedDataUnlocker implements DataUnlocker {

    @Override
    public byte[] unlock(byte[] data, PasswordInfo passwordInfo) {
        return new byte[0];
    }
}
