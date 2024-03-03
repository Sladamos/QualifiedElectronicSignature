package pg.proj.pg.event.entity.api;

import pg.proj.pg.event.receiver.api.NoArgsEventReceiver;

public interface NoArgsEvent {
    void addListener(NoArgsEventReceiver listener);
    void removeListener(NoArgsEventReceiver listener);
    void invoke();
    void removeAllListeners();
}
