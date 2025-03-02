package pg.proj.pg.error;

public interface CriticalErrorReceiver {
    void onCriticalErrorOccurred(CriticalAppError error);
}
