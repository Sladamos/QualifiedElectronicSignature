package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.container.CipherContainer;

@AllArgsConstructor
public class EncryptedRsaCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherContainer cipherContainer;

    @Override
    public CipherContainer getCipher() {
        //TODO ask for password
        return null;
    }

}
