package pg.proj.pg.error.layer;

import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.BasicErrorReceiver;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.error.CriticalErrorReceiver;
import pg.proj.pg.event.OneArgEvent;
import pg.proj.pg.event.OneArgEventImpl;

public class ErrorHandlingLayerImpl implements ErrorHandlingLayer {

    private final OneArgEvent<CriticalAppError> criticalErrorEvent;

    private final OneArgEvent<BasicAppError> basicErrorEvent;

    public ErrorHandlingLayerImpl() {
        criticalErrorEvent = new OneArgEventImpl<>();
        basicErrorEvent = new OneArgEventImpl<>();
    }

    @Override
    public void runInErrorHandler(Runnable runnable) {
        try {
            runnable.run();
        } catch (CriticalAppError err) {
            criticalErrorEvent.invoke(err);
        } catch (BasicAppError err) {
            basicErrorEvent.invoke(err);
        } catch (Exception err) {
            notifyAboutUnspecifiedException();
        }
    }

    @Override
    public void registerBasicErrorReceiver(BasicErrorReceiver errorReceiver) {
        basicErrorEvent.addListener(errorReceiver::onBasicErrorOccurred);
    }

    @Override
    public void registerCriticalErrorReceiver(CriticalErrorReceiver errorReceiver) {
        criticalErrorEvent.addListener(errorReceiver::onCriticalErrorOccurred);
    }

    private void notifyAboutUnspecifiedException() {
        CriticalAppError criticalError = new CriticalAppError("Something went very wrong");
        criticalErrorEvent.invoke(criticalError);
    }

}
