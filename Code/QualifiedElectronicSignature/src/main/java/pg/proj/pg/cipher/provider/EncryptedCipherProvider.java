package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.unlocker.CipherInfoUnlocker;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.function.Supplier;

@AllArgsConstructor
public class EncryptedCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherInfoUnlocker cipherInfoUnlocker;

    private final Supplier<CipherInfo> cipherInfoSupplier;

    @Override
    public CipherExecutioner getCipher() {
        PasswordInfo password = new PasswordInfo("4554"); //TODO: ask for pin
        CipherInfo cipherInfo = cipherInfoSupplier.get();
        CipherInfo unlockedCipherInfo = cipherInfoUnlocker.unlock(cipherInfo, password);
        try {
            return new CipherExecutionerImpl(unlockedCipherInfo);
        } catch (Exception e) {
            throw new BasicAppError("Cannot decrypt key: " + e.getMessage());
        }
    }
}
