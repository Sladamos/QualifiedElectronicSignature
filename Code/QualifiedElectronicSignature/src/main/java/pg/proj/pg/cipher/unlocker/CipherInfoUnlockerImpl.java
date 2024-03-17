package pg.proj.pg.cipher.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.password.info.PasswordInfo;

@AllArgsConstructor
public class CipherInfoUnlockerImpl implements CipherInfoUnlocker {

    private final KeyInfoUnlocker keyInfoUnlocker;

    @Override
    public CipherInfo unlock(CipherInfo source, PasswordInfo password) {
        KeyInfo keyInfo = source.keyInfo();
        KeyInfo unlockedKeyInfo = keyInfoUnlocker.unlock(keyInfo, password);
        return new CipherInfo(source.cipher(), source.keyGen(), unlockedKeyInfo, source.cipherType());
    }
}
