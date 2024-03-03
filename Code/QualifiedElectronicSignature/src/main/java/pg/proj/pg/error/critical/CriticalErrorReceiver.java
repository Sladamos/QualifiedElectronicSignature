package pg.proj.pg.error.critical;

public interface CriticalErrorReceiver {
    void onCriticalErrorOccurred(CriticalAppError error);
}
