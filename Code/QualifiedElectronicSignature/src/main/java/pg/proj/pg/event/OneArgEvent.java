package pg.proj.pg.event;

public interface OneArgEvent<T> {
    void addListener(OneArgEventReceiver<T> listener);
    void removeListener(OneArgEventReceiver<T> listener);
    void invoke(T arg);
    void removeAllListeners();
}
