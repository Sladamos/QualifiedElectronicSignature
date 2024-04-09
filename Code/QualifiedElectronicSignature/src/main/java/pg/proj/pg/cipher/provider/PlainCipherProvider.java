package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.initializer.CipherInitializer;

import java.util.function.Supplier;

@AllArgsConstructor
public class PlainCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherInitializer cipherInitializer;

    private final Supplier<CipherInfo> cipherInfoSupplier;

    @Override
    public CipherExecutioner getCipher() {
        CipherInfo cipherInfo = cipherInfoSupplier.get();
        return new CipherExecutionerImpl(cipherInfo, cipherInitializer);
    }
}
