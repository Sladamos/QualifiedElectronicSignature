package pg.proj.pg.key.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.Base64;

@AllArgsConstructor
public class RsaKeyInfoUnlocker implements KeyInfoUnlocker {

    private final DataUnlocker dataUnlocker;

    @Override
    public KeyInfo unlock(KeyInfo source, PasswordInfo passwordInfo) {
        if (source == null || passwordInfo == null) {
            throw new CriticalAppError("Incorrect arguments in key unlocker");
        }
        byte[] content = source.keyContent();
        byte[] unlockedContent = dataUnlocker.unlock(content, passwordInfo);
        byte[] decodedKeyBytes = Base64.getDecoder().decode(unlockedContent);
        return new KeyInfo(decodedKeyBytes);
    }
}
