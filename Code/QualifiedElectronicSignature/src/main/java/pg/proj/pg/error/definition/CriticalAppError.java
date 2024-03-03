package pg.proj.pg.error.definition;

public class CriticalAppError extends RuntimeException {

    public CriticalAppError(String message) {
        super(message);
    }

}
