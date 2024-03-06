package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.container.CipherContainer;

@AllArgsConstructor
public class EncryptedCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherContainer cipherContainer;

    @Override
    public CipherContainer getCipher() {
        //TODO ask for password and verify it
        return cipherContainer;
    }

}
