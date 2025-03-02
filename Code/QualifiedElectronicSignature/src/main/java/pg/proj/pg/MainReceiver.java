package pg.proj.pg;

import lombok.AllArgsConstructor;
import pg.proj.pg.communicate.Communicate;
import pg.proj.pg.communicate.CommunicateReceiver;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.BasicErrorReceiver;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.error.CriticalErrorReceiver;

import java.util.function.Consumer;

@AllArgsConstructor
public class MainReceiver implements CriticalErrorReceiver, BasicErrorReceiver, CommunicateReceiver {

    private Consumer<String> errorsDisplayer;

    private Consumer<String> communicatesDisplayer;

    private Runnable exitOption;

    @Override
    public void onBasicErrorOccurred(BasicAppError error) {
        errorsDisplayer.accept(error.getMessage());
    }

    @Override
    public void onCriticalErrorOccurred(CriticalAppError error) {
        String message = error.getMessage();
        errorsDisplayer.accept(message);
        exitOption.run();
    }

    @Override
    public void onCommunicateOccurred(Communicate communicate) {
        String content = communicate.content();
        communicatesDisplayer.accept(content);
    }
}
