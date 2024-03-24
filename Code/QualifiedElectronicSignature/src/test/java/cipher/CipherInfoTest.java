package cipher;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;

import static org.assertj.core.api.Assertions.*;

public class CipherInfoTest {

    @Test
    public void should_returnAesAlgorithm_when_getAlgorithmTypeCalledOnAesCipherType() {
        CipherType cipherType = CipherType.AES;
        assertThat(cipherType.getAlgorithmType()).isEqualTo("AES");

    }

    @Test
    public void should_returnAesValue_when_getStrValueCalledOnAesCipherType() {
        CipherType cipherType = CipherType.AES;
        assertThat(cipherType.getStrValue()).isEqualTo("AES/CBC/PKCS5Padding");

    }

    @Test
    public void should_returnRsaAlgorithm_when_getAlgorithmTypeCalledOnRsaCipherType() {
        CipherType cipherType = CipherType.RSA;
        assertThat(cipherType.getAlgorithmType()).isEqualTo("RSA");

    }

    @Test
    public void should_returnRsaValue_when_getStrValueCalledOnRsaCipherType() {
        CipherType cipherType = CipherType.RSA;
        assertThat(cipherType.getStrValue()).isEqualTo("RSA");

    }
}
