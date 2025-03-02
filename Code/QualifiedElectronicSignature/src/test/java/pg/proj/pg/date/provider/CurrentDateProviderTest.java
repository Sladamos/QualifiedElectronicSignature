package pg.proj.pg.date.provider;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class CurrentDateProviderTest {

    private final CurrentDateProvider uut = new CurrentDateProvider();

    @Test
    void shouldReturnCurrentDate() {
        assertThat(uut.getDate()).isCloseTo(LocalDateTime.now(), within(15, ChronoUnit.SECONDS));
    }

}