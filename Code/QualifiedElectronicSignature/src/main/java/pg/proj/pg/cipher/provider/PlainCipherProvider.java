package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.executioner.CipherExecutioner;

import java.util.function.Supplier;

@AllArgsConstructor
public class PlainCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final Supplier<CipherExecutioner> cipherContainer;

    @Override
    public CipherExecutioner getCipher() {
        return cipherContainer.get();
    }
}
