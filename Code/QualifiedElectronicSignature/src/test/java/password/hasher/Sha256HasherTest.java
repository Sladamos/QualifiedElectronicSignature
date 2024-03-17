package password.hasher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pg.proj.pg.data.hasher.Hasher;
import pg.proj.pg.data.hasher.Sha256Hasher;

import static org.assertj.core.api.Assertions.*;

public class Sha256HasherTest {

    private Hasher hasher;

    @BeforeEach
    void initializeHasher() {
        hasher = new Sha256Hasher();
    }

    @Test
    public void should_return32LengthArr_when_EmptyStrPassed() {
        byte[] result = hasher.hashStr("");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneLetterPassed() {
        byte[] result = hasher.hashStr("x");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneDigitPassed() {
        byte[] result = hasher.hashStr("9");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleCharsPassed() {
        byte[] result = hasher.hashStr("abc");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleDigitsPassed() {
        byte[] result = hasher.hashStr("1234");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_CombinedDigitsAndCharsPassed() {
        byte[] result = hasher.hashStr("1234abc4321");
        assertThat(result.length).isEqualTo(32);
    }
}
