package pg.proj.pg.key.generator;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.CriticalAppError;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SecretKeyGenTest {

    private final String cipherType = CipherType.AES.getStrValue();

    private final byte[] keyBytes = new byte[]{1, 2 ,3};

    private final SecretKeyGen uut = new SecretKeyGen();

    @Test
    public void shouldThrowCriticalAppErrorWhenNullCipherTypePassedToGenerateKey() {
        assertThatThrownBy(() -> uut.generateKey(keyBytes, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldThrowCriticalAppErrorWhenNullBytesPassedToGenerateKey() {
        assertThatThrownBy(() -> uut.generateKey(null, cipherType)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenValidCipherTypeAndBytesPassedToGenerateKey() {
        assertThatCode(() -> uut.generateKey(keyBytes, cipherType)).doesNotThrowAnyException();
    }
}
