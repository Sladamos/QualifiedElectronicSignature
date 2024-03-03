package pg.proj.pg.error.layer;

import pg.proj.pg.error.basic.BasicAppError;
import pg.proj.pg.error.basic.BasicErrorReceiver;
import pg.proj.pg.error.critical.CriticalAppError;
import pg.proj.pg.error.critical.CriticalErrorReceiver;

public class ErrorHandlingLayerImpl implements ErrorHandlingLayer {

    private BasicErrorReceiver basicErrorReceiver;

    private CriticalErrorReceiver criticalErrorReceiver;

    @Override
    public void runInErrorHandler(Runnable runnable) {
        try {
            runnable.run();
        } catch (CriticalAppError err) {
            criticalErrorReceiver.onCriticalErrorOccurred(err);
        } catch (BasicAppError err) {
            basicErrorReceiver.onBasicErrorOccurred(err);
        } catch (Exception err) {
            notifyAboutUnspecifiedException(err);
        }
    }

    @Override
    public void registerBasicErrorReceiver(BasicErrorReceiver errorReceiver) {

    }

    @Override
    public void registerCriticalErrorReceiver(CriticalErrorReceiver errorReceiver) {

    }

    private void notifyAboutUnspecifiedException(Exception err) {
        CriticalAppError criticalError = new CriticalAppError("Something went very wrong. " + err.getMessage());
        criticalErrorReceiver.onCriticalErrorOccurred(criticalError);
    }

}
