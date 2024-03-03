package pg.proj.pg.error.entity.impl;

public class CriticalAppError extends RuntimeException {

    public CriticalAppError(String message) {
        super(message);
    }

}
