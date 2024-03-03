package pg.proj.pg.event.entity.api;

import pg.proj.pg.event.receiver.api.OneArgEventReceiver;

public interface OneArgEvent<T> {
    void addListener(OneArgEventReceiver<T> listener);
    void removeListener(OneArgEventReceiver<T> listener);
    void invoke(T arg);
    void removeAllListeners();
}
