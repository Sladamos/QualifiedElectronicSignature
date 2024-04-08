package pg.proj.pg.date.provider;

import java.time.LocalDateTime;

public class CurrentDateProvider implements DateProvider {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
