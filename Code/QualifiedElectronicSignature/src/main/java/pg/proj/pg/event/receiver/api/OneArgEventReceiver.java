package pg.proj.pg.event.receiver.api;

public interface OneArgEventReceiver<T> {
    void onOneArgEventOccurred(T arg);
}
