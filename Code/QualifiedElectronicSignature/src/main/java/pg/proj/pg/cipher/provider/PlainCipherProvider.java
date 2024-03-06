package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.container.CipherContainer;

@Getter
@AllArgsConstructor
public class PlainCipherProvider implements CipherProvider {

    private final String uniqueName;

    private final CipherContainer cipher;

}
