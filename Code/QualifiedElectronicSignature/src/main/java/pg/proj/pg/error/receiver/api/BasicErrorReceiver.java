package pg.proj.pg.error.receiver.api;

import pg.proj.pg.error.entity.impl.BasicAppError;

public interface BasicErrorReceiver {
    void onBasicErrorOccurred(BasicAppError error);
}
