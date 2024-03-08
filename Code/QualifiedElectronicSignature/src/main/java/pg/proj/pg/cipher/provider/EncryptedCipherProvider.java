package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.info.CipherInfo;

import java.util.function.Supplier;

@AllArgsConstructor
public class EncryptedCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final Supplier<CipherInfo> cipherInfoSupplier;

    @Override
    public CipherExecutioner getCipher() {
        //TODO ask for password and modify keyStr
        CipherInfo cipherInfo = cipherInfoSupplier.get();
        return new CipherExecutionerImpl(new CipherInfo(cipherInfo.cipher(),
                cipherInfo.keyGen(), cipherInfo.keyStr(), cipherInfo.cipherType()));
    }

}
