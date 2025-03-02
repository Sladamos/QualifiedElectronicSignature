package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.info.EncryptedCipherInfo;
import pg.proj.pg.cipher.initializer.CipherInitializer;
import pg.proj.pg.cipher.unlocker.CipherInfoUnlocker;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.password.provider.PasswordProvider;
import pg.proj.pg.password.selector.PasswordSelector;

import java.util.function.Supplier;

@AllArgsConstructor
public class EncryptedCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherInfoUnlocker cipherInfoUnlocker;

    private final PasswordSelector passwordSelector;

    private final CipherInitializer cipherInitializer;

    private final Supplier<EncryptedCipherInfo> cipherInfoSupplier;

    @Override
    public CipherExecutioner getCipher() {
        EncryptedCipherInfo cipherInfo = cipherInfoSupplier.get();
        PasswordProvider passwordProvider = passwordSelector.selectPassword();
        PasswordInfo password = passwordProvider.getPasswordInfo();
        CipherInfo unlockedCipherInfo = cipherInfoUnlocker.unlock(cipherInfo, password);
        try {
            return new CipherExecutionerImpl(unlockedCipherInfo, cipherInitializer);
        } catch (Exception e) {
            throw new BasicAppError("Cannot decrypt key: " + e.getMessage());
        }
    }
}
