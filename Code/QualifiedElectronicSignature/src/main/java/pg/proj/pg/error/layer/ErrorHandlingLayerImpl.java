package pg.proj.pg.error.layer;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.receiver.api.BasicErrorReceiver;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.error.receiver.api.CriticalErrorReceiver;
import pg.proj.pg.event.entity.api.OneArgEvent;

public class ErrorHandlingLayerImpl implements ErrorHandlingLayer {

    private final OneArgEvent<CriticalAppError> criticalErrorEvent;

    private final OneArgEvent<BasicAppError> basicErrorEvent;

    public ErrorHandlingLayerImpl() {

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
            notifyAboutUnspecifiedException(err);
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

    private void notifyAboutUnspecifiedException(Exception err) {
        CriticalAppError criticalError = new CriticalAppError("Something went very wrong. " + err.getMessage());
        criticalErrorEvent.invoke(criticalError);
    }

}
