package pg.proj.pg;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.error.receiver.api.BasicErrorReceiver;
import pg.proj.pg.error.receiver.api.CriticalErrorReceiver;

public class MainReceiver implements CriticalErrorReceiver, BasicErrorReceiver {

    @Override
    public void onBasicErrorOccurred(BasicAppError error) {
        System.out.println("Basic error not implemented");
    }

    @Override
    public void onCriticalErrorOccurred(CriticalAppError error) {
        System.out.println("Critical error not implemented");
    }

}
