package pg.proj.pg.error.receiver.api;

import pg.proj.pg.error.entity.impl.CriticalAppError;

public interface CriticalErrorReceiver {
    void onCriticalErrorOccurred(CriticalAppError error);
}
