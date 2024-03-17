package utils.hasher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pg.proj.pg.utils.hasher.Hasher;

import static org.assertj.core.api.Assertions.*;

public class Sha256HasherTest {

    private Hasher hasher;

    @BeforeEach
    void initializeHasher() {
        hasher = new
    }

    @Test
    public void should_return32LengthArr_when_EmptyStrPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneLetterPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("x");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneDigitPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("9");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleCharsPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("abc");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleDigitsPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("1234");
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_CombinedDigitsAndCharsPassed() {
        Hasher hasher = null;
        byte[] result = hasher.hashStr("1234abc4321");
        assertThat(result.length).isEqualTo(32);
    }
}
