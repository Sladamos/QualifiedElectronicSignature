package pg.proj.pg.error.layer;

import pg.proj.pg.error.basic.BasicErrorReceiver;
import pg.proj.pg.error.critical.CriticalErrorReceiver;

public interface ErrorHandlingLayer {
    void runInErrorHandler(Runnable runnable);
    void registerBasicErrorReceiver(BasicErrorReceiver errorReceiver);
    void registerCriticalErrorReceiver(CriticalErrorReceiver errorReceiver);
}
