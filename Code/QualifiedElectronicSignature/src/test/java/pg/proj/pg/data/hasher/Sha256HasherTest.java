package pg.proj.pg.data.hasher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Sha256HasherTest {

    private final Sha256Hasher uut = new Sha256Hasher();

    @Test
    public void shouldReturn32LengthArrWhenEmptyStrPassedToHash() {
        byte[] result = uut.hash("".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void shouldReturn32LengthArrWhenOneLetterPassedToHash() {
        byte[] result = uut.hash("x".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void shouldReturn32LengthArrWhenOneDigitPassedToHash() {
        byte[] result = uut.hash("9".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void shouldReturn32LengthArrWhenMultipleCharsPassedToHash() {
        byte[] result = uut.hash("abc".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void shouldReturn32LengthArrWhenMultipleDigitsPassedToHash() {
        byte[] result = uut.hash("1234".getBytes());
        assertThat(result.length).isEqualTo(32);
    }

    @Test
    public void shouldReturn32LengthArrWhenCombinedDigitsAndCharsPassedToHash() {
        byte[] result = uut.hash("1234abc4321".getBytes());
        assertThat(result.length).isEqualTo(32);
    }
}
