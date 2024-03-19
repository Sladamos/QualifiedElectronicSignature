package pg.proj.pg.data.unlocker;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.data.hasher.Hasher;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.function.Function;

@AllArgsConstructor
public class HashedDataUnlocker implements DataUnlocker {

    private final Hasher hasher;

    private final Function<KeyInfo, CipherExecutioner> cipherExecutionerGen;

    @Override
    public byte[] unlock(byte[] data, PasswordInfo passwordInfo) {
        byte[] passwordAsBytes = passwordInfo.content().getBytes();
        byte[] hashedPassword = hasher.hash(passwordAsBytes);
        KeyInfo keyInfo = new KeyInfo(hashedPassword);
        CipherExecutioner executioner = cipherExecutionerGen.apply(keyInfo);
        return executioner.decrypt(data);
    }
}
