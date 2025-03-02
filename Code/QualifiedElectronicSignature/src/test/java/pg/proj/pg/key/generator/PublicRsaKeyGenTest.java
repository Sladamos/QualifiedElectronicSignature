package pg.proj.pg.key.generator;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.CriticalAppError;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PublicRsaKeyGenTest {

    private final byte[] keyBytes = KeyPairGenerator.getInstance("RSA")
            .generateKeyPair()
            .getPublic()
            .getEncoded();

    private final String cipherType = CipherType.RSA.getStrValue();

    private final PublicRsaKeyGen uut = new PublicRsaKeyGen();

    public PublicRsaKeyGenTest() throws NoSuchAlgorithmException {
    }

    @Test
    public void shouldThrowCriticalAppErrorWhenNullCipherTypePassedToGenerateKey() {
        assertThatThrownBy(() -> uut.generateKey(keyBytes, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldThrowCriticalAppErrorWhenNullBytesPassedToGenerateKey() {
        assertThatThrownBy(() -> uut.generateKey(null, cipherType)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldThrowBasicAppErrorWhenInvalidCipherTypePassedToGenerateKey() {
        String invalidCipherType = CipherType.AES.getStrValue();
        assertThatThrownBy(() -> uut.generateKey(keyBytes, invalidCipherType)).isInstanceOf(BasicAppError.class);
    }

    @Test
    public void shouldThrowBasicAppErrorWhenInvalidBytesPassedToGenerateKey() {
        byte[] invalidBytes = new byte[]{1, 2, 3};
        assertThatThrownBy(() -> uut.generateKey(invalidBytes, cipherType)).isInstanceOf(BasicAppError.class);
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenValidCipherTypeAndBytesPassedToGenerateKey() {
        assertThatCode(() -> uut.generateKey(keyBytes, cipherType)).doesNotThrowAnyException();
    }
}
