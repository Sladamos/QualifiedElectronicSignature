package pg.proj.pg.signature.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;

@AllArgsConstructor
public class SignatureExecutionerInfoUnlockerImpl implements SignatureExecutionerInfoUnlocker {

    private final KeyInfoUnlocker keyInfoUnlocker;

    @Override
    public SignatureExecutionerInfo unlock(SignatureExecutionerInfo source, PasswordInfo password) {
        KeyInfo keyInfo = source.keyInfo();
        KeyInfo unlockedKeyInfo = keyInfoUnlocker.unlock(keyInfo, password);
        return new SignatureExecutionerInfo(source.signature(),
                source.keyGen(), unlockedKeyInfo, source.signatureType());
    }
}
