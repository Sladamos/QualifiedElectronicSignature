package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.cipher.container.CipherContainerImpl;

import java.util.function.Supplier;

@AllArgsConstructor
public class PlainCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final Supplier<CipherContainer> cipherContainer;

    @Override
    public CipherContainer getCipher() {
        return cipherContainer.get();
    }
}
