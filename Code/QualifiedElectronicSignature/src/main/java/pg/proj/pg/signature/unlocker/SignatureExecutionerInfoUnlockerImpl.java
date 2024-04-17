package pg.proj.pg.signature.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.KeyInfoUnlocker;
import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.signature.info.EncryptedSignatureExecutionerInfo;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;

@AllArgsConstructor
public class SignatureExecutionerInfoUnlockerImpl implements SignatureExecutionerInfoUnlocker {

    private final KeyInfoUnlocker keyInfoUnlocker;

    @Override
    public SignatureExecutionerInfo unlock(EncryptedSignatureExecutionerInfo encryptedSource, PasswordInfo password) {
        SignatureExecutionerInfo source = encryptedSource.signatureExecutionerInfo();
        KeyInfo keyInfo = source.keyInfo();
        KeyInfo unlockedKeyInfo = keyInfoUnlocker.unlock(keyInfo, password, encryptedSource.iv());
        return new SignatureExecutionerInfo(source.signature(),
                source.keyGen(), unlockedKeyInfo, source.signatureType());
    }
}
