package pg.proj.pg.error.layer;

import pg.proj.pg.error.receiver.api.BasicErrorReceiver;
import pg.proj.pg.error.receiver.api.CriticalErrorReceiver;

public interface ErrorHandlingLayer {
    void runInErrorHandler(Runnable runnable);
    void registerBasicErrorReceiver(BasicErrorReceiver errorReceiver);
    void registerCriticalErrorReceiver(CriticalErrorReceiver errorReceiver);
}
