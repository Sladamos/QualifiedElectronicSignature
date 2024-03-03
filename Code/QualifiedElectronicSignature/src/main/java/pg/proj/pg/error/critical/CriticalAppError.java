package pg.proj.pg.error.critical;

public class CriticalAppError extends RuntimeException {

    public CriticalAppError(String message) {
        super(message);
    }

}
