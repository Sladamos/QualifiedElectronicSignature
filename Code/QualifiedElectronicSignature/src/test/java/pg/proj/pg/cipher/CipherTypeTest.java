package pg.proj.pg.cipher;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;

import static org.assertj.core.api.Assertions.assertThat;

public class CipherTypeTest {

    @Test
    public void shouldReturnAesAlgorithmWhenGetAlgorithmTypeCalledOnAesCipherType() {
        CipherType cipherType = CipherType.AES;
        assertThat(cipherType.getAlgorithmType()).isEqualTo("AES");

    }

    @Test
    public void shouldReturnAesValueWhenGetStrValueCalledOnAesCipherType() {
        CipherType cipherType = CipherType.AES;
        assertThat(cipherType.getStrValue()).isEqualTo("AES/CBC/PKCS5Padding");

    }

    @Test
    public void shouldReturnRsaAlgorithmWhenGetAlgorithmTypeCalledOnRsaCipherType() {
        CipherType cipherType = CipherType.RSA;
        assertThat(cipherType.getAlgorithmType()).isEqualTo("RSA");

    }

    @Test
    public void shouldReturnRsaValueWhenGetStrValueCalledOnRsaCipherType() {
        CipherType cipherType = CipherType.RSA;
        assertThat(cipherType.getStrValue()).isEqualTo("RSA");

    }
}
