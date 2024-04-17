package pg.proj.pg.key.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.Base64;

@AllArgsConstructor
public class RsaKeyInfoUnlocker implements KeyInfoUnlocker {

    private final DataUnlocker dataUnlocker;

    @Override
    public KeyInfo unlock(KeyInfo source, PasswordInfo passwordInfo, InitializationVector iv) {
        byte[] content = source.keyContent();
        byte[] unlockedContent = dataUnlocker.unlock(content, passwordInfo, iv);
        byte[] decodedKeyBytes = Base64.getDecoder().decode(unlockedContent);
        return new KeyInfo(decodedKeyBytes);
    }
}
