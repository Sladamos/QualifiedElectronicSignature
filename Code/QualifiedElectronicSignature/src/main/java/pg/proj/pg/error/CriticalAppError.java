package pg.proj.pg.error;

public class CriticalAppError extends RuntimeException {

    public CriticalAppError(String message) {
        super(message);
    }

}
