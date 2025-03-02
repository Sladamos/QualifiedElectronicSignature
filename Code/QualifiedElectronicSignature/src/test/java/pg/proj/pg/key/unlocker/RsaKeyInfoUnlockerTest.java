package pg.proj.pg.key.unlocker;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pg.proj.pg.data.unlocker.DataUnlocker;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.password.info.PasswordInfo;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RsaKeyInfoUnlockerTest {

    private final String password = new String(new byte[]{3, 2, 1});

    private final byte[] keyContent = new byte[]{1, 2, 5};

    private final byte[] unlockedKeyContent = Base64.getEncoder().encode(new byte[]{3, 52, 12, 14});

    @Test
    public void shouldThrowCriticalAppErrorWhenNullKeyInfoPassedToUnlock() {
        PasswordInfo passwordInfo = new PasswordInfo(password);
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker uut = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> uut.unlock(null, passwordInfo, iv)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldThrowCriticalAppErrorWhenNullPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(null, iv);
        RsaKeyInfoUnlocker uut = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> uut.unlock(source, null, iv)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldThrowCriticalAppErrorWhenNullIvPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo(password);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, null);
        RsaKeyInfoUnlocker uut = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThatThrownBy(() -> uut.unlock(source, passwordInfo, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldReturnUnlockedKeyContentWhenEmptyPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("");
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker uut = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(uut.unlock(source, passwordInfo, iv).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    @Test
    public void shouldReturnUnlockedKeyContentWhenNotEmptyPasswordInfoPassedToUnlock() {
        KeyInfo source = new KeyInfo(keyContent);
        PasswordInfo passwordInfo = new PasswordInfo("random");
        InitializationVector iv = new InitializationVector(new byte[0]);
        DataUnlocker dataUnlocker = mockDataUnlocker(passwordInfo, iv);
        RsaKeyInfoUnlocker uut = new RsaKeyInfoUnlocker(dataUnlocker);
        assertThat(uut.unlock(source, passwordInfo, iv).keyContent())
                .isEqualTo(Base64.getDecoder().decode(unlockedKeyContent));
    }

    private DataUnlocker mockDataUnlocker(PasswordInfo passwordInfo, InitializationVector iv) {
        DataUnlocker dataUnlocker = Mockito.mock(DataUnlocker.class);
        Mockito.when(dataUnlocker.unlock(keyContent, passwordInfo, iv)).thenReturn(unlockedKeyContent);
        return dataUnlocker;
    }

}
