package data.hasher;

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
        byte[] result = hasher.hash("".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneLetterPassed() {
        byte[] result = hasher.hash("x".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_OneDigitPassed() {
        byte[] result = hasher.hash("9".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleCharsPassed() {
        byte[] result = hasher.hash("abc".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_MultipleDigitsPassed() {
        byte[] result = hasher.hash("1234".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void should_return32LengthArr_when_CombinedDigitsAndCharsPassed() {
        byte[] result = hasher.hash("1234abc4321".getBytes());
        assertThat(result.length).isEqualTo(32);
    }
}
