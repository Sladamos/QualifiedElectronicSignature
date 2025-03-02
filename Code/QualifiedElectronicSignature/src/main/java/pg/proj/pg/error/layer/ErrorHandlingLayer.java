package pg.proj.pg.error.layer;

import pg.proj.pg.error.BasicErrorReceiver;
import pg.proj.pg.error.CriticalErrorReceiver;

public interface ErrorHandlingLayer {
    void runInErrorHandler(Runnable runnable);
    void registerBasicErrorReceiver(BasicErrorReceiver errorReceiver);
    void registerCriticalErrorReceiver(CriticalErrorReceiver errorReceiver);
}
