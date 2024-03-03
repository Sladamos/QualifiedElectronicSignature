package pg.proj.pg.error.receiver.api;

import pg.proj.pg.error.definition.CriticalAppError;

public interface CriticalErrorReceiver {
    void onCriticalErrorOccurred(CriticalAppError error);
}
