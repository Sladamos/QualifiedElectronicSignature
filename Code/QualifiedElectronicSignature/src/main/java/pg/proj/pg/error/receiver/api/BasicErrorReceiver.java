package pg.proj.pg.error.receiver.api;

import pg.proj.pg.error.definition.BasicAppError;

public interface BasicErrorReceiver {
    void onBasicErrorOccurred(BasicAppError error);
}
