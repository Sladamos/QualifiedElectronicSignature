package pg.proj.pg.event;

public interface OneArgEventReceiver<T> {
    void onOneArgEventOccurred(T arg);
}
