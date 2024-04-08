package pg.proj.pg.signature.unlocker;

import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;

public interface SignatureExecutionerInfoUnlocker {
    SignatureExecutionerInfo unlock(SignatureExecutionerInfo source, PasswordInfo password);
}
