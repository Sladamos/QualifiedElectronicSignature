package key.generator;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.key.generator.PublicRsaKeyGen;
import pg.proj.pg.key.generator.SecretKeyGen;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SecretKeyGenTest {

    private final String cipherType = CipherType.AES.getStrValue();

    private final byte[] keyBytes = new byte[]{1, 2 ,3};

    private final SecretKeyGen keyGen = new SecretKeyGen();

    @Test
    public void should_throwCriticalAppError_when_nullCipherTypePassedToGenerateKey() {
        assertThatThrownBy(() -> keyGen.generateKey(keyBytes, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullBytesPassedToGenerateKey() {
        assertThatThrownBy(() -> keyGen.generateKey(null, cipherType)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_notThrowAnyException_when_validCipherTypeAndBytesPassedToGenerateKey() {
        assertThatCode(() -> keyGen.generateKey(keyBytes, cipherType)).doesNotThrowAnyException();
    }
}
