package key.unlocker;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.unlocker.RsaKeyInfoUnlocker;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

public class RsaKeyInfoUnlockerTest {

    private final String password = new String(new byte[]{3, 2, 1});

    private final byte[] keyContent = new byte[]{1, 2, 5};

    private final byte[] unlockedKeyContent = Base64.getEncoder().encode(new byte[]{3, 52, 12, 14});

    @Test
    public void should_throwCriticalAppError_when_nullKeyInfoPassedToUnlock() {
        PasswordInfo passwordInfo = new PasswordInfo(password);
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> keyInfoUnlocker.unlock(null, passwordInfo, iv)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(null, iv);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> keyInfoUnlocker.unlock(source, null, iv)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullIvPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo(password);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, null);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> keyInfoUnlocker.unlock(source, passwordInfo, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_returnUnlockedKeyContent_when_emptyPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("");
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(keyInfoUnlocker.unlock(source, passwordInfo, iv).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    @Test
    public void should_returnUnlockedKeyContent_when_notEmptyPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("random");
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(keyInfoUnlocker.unlock(source, passwordInfo, iv).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    private DataUnlocker mockDataUnlocker(PasswordInfo passwordInfo, InitializationVector iv) {
        DataUnlocker dataUnlocker = Mockito.mock(DataUnlocker.class);
        Mockito.when(dataUnlocker.unlock(keyContent, passwordInfo, iv)).thenReturn(unlockedKeyContent);
        return dataUnlocker;
    }

}
