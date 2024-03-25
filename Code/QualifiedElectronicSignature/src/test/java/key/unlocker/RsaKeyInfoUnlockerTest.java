package key.unlocker;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.error.definition.CriticalAppError;
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
    public void should_throwCriticalAppError_when_nullKeyInfoProvidedToUnlock() {
        PasswordInfo passwordInfo = new PasswordInfo(password);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> keyInfoUnlocker.unlock(null, passwordInfo)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullPasswordInfoProvidedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        DataUnlocker dataUnlocker = mockDataUnlocker(null);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> keyInfoUnlocker.unlock(source, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_returnUnlockedKeyContent_when_emptyPasswordInfoProvidedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("");
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(keyInfoUnlocker.unlock(source, passwordInfo).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    @Test
    public void should_returnUnlockedKeyContent_when_notEmptyPasswordInfoProvidedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("random");
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo);
        RsaKeyInfoUnlocker keyInfoUnlocker = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(keyInfoUnlocker.unlock(source, passwordInfo).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    private DataUnlocker mockDataUnlocker(PasswordInfo passwordInfo) {
        DataUnlocker dataUnlocker = Mockito.mock(DataUnlocker.class);
        Mockito.when(dataUnlocker.unlock(keyContent, passwordInfo)).thenReturn(unlockedKeyContent);
        return dataUnlocker;
    }

}
