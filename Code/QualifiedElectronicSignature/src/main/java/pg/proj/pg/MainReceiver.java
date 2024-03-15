package pg.proj.pg;

import lombok.AllArgsConstructor;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.error.receiver.api.BasicErrorReceiver;
import pg.proj.pg.error.receiver.api.CriticalErrorReceiver;

import java.util.function.Consumer;

@AllArgsConstructor
public class MainReceiver implements CriticalErrorReceiver, BasicErrorReceiver {

    private Consumer<String> errorsDisplayer;

    private Runnable exitOption;

    @Override
    public void onBasicErrorOccurred(BasicAppError error) {
        errorsDisplayer.accept(error.getMessage());
    }

    @Override
    public void onCriticalErrorOccurred(CriticalAppError error) {
        errorsDisplayer.accept(error.getMessage());
        exitOption.run();
    }

}
