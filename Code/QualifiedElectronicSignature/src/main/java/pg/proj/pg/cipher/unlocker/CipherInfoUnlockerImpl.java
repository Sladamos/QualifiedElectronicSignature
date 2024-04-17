package pg.proj.pg.cipher.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.info.EncryptedCipherInfo;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.password.info.PasswordInfo;

@AllArgsConstructor
public class CipherInfoUnlockerImpl implements CipherInfoUnlocker {

    private final KeyInfoUnlocker keyInfoUnlocker;

    @Override
    public CipherInfo unlock(EncryptedCipherInfo encryptedSource, PasswordInfo password) {
        CipherInfo source = encryptedSource.cipherInfo();
        KeyInfo keyInfo = source.keyInfo();
        KeyInfo unlockedKeyInfo = keyInfoUnlocker.unlock(keyInfo, password, encryptedSource.iv());
        return new CipherInfo(source.cipher(),
                source.keyGen(),
                unlockedKeyInfo,
                source.cipherType());
    }
}
