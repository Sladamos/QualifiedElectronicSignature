package pg.proj.pg.key.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.info.KeyInfoImpl;
import pg.proj.pg.password.info.PasswordInfo;

@AllArgsConstructor
public class KeyInfoUnlockerImpl implements KeyInfoUnlocker {

    private final DataUnlocker dataUnlocker;

    @Override
    public KeyInfo unlock(KeyInfo source, PasswordInfo passwordInfo) {
        byte[] content = source.keyContent();
        byte[] unlockedContent = dataUnlocker.unlock(content, passwordInfo);
        return new KeyInfoImpl(unlockedContent);
    }
}
