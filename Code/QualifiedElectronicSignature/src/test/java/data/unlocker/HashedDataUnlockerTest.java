package data.unlocker;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.data.hasher.Hasher;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.data.unlocker.HashedDataUnlocker;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

public class HashedDataUnlockerTest {

    private final byte[] rawDataArr = new byte[]{1, 2, 3};

    private final byte[] decryptedDataArr = new byte[]{3, 6, 9};

    private final String password = new String(new byte[]{3, 2, 1});

    private final byte[] passwordHashedArr = new byte[]{5, 1, 7};

    @Test
    public void should_throwCriticalAppError_when_nullDataProvidedToUnlock() {
        Hasher hasher = createDefaultMockForHasher();
        Function<KeyInfo, CipherExecutioner> cipherExecutionerGen = createDefaultMockForCipherExecutionerGen();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, cipherExecutionerGen);
        PasswordInfo passwordInfo = new PasswordInfo(password);
        assertThatThrownBy(() -> dataUnlocker.unlock(null, passwordInfo)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullPasswordProvidedToUnlock() {
        Hasher hasher = createDefaultMockForHasher();
        Function<KeyInfo, CipherExecutioner> cipherExecutionerGen = createDefaultMockForCipherExecutionerGen();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, cipherExecutionerGen);
        assertThatThrownBy(() -> dataUnlocker.unlock(rawDataArr, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_returnDecryptedArray_when_rawDataWithEmptyPasswordProvidedToUnlock() {
        Hasher hasher = createDefaultMockForHasher();
        Function<KeyInfo, CipherExecutioner> cipherExecutionerGen = createDefaultMockForCipherExecutionerGen();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, cipherExecutionerGen);
        PasswordInfo passwordInfo = new PasswordInfo("");
        assertThat(dataUnlocker.unlock(rawDataArr, passwordInfo)).isEqualTo(decryptedDataArr);
    }

    @Test
    public void should_returnDecryptedArray_when_rawDataWithSomePasswordProvidedToUnlock() {
        Hasher hasher = createDefaultMockForHasher();
        Function<KeyInfo, CipherExecutioner> cipherExecutionerGen = createDefaultMockForCipherExecutionerGen();
        DataUnlocker dataUnlocker = new HashedDataUnlocker(hasher, cipherExecutionerGen);
        PasswordInfo passwordInfo = new PasswordInfo(password);
        assertThat(dataUnlocker.unlock(rawDataArr, passwordInfo)).isEqualTo(decryptedDataArr);
    }

    private Function<KeyInfo, CipherExecutioner> createDefaultMockForCipherExecutionerGen() {
        CipherExecutioner cipherExecutioner = Mockito.mock(CipherExecutioner.class);
        Mockito.when(cipherExecutioner.decrypt(rawDataArr)).thenReturn(decryptedDataArr);
        return (keyInfo) -> cipherExecutioner;
    }

    private Hasher createDefaultMockForHasher() {
        Hasher hasher = Mockito.mock(Hasher.class);
        Mockito.when(hasher.hash(password.getBytes())).thenReturn(passwordHashedArr);
        return hasher;
    }

}
