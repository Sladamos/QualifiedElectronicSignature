package pg.proj.pg.cipher.provider;

import lombok.Getter;
import pg.proj.pg.cipher.container.CipherContainer;

public class EncryptedRsaCipherProvider implements CipherProvider {

    private final CipherContainer cipherContainer;

    @Getter
    private final String uniqueName = "EncryptedRsa";

    public EncryptedRsaCipherProvider(CipherContainer cipherContainer) {
        this.cipherContainer = cipherContainer;
    }

    @Override
    public CipherContainer getCipher() {
        return null;
    }

}
