package pg.proj.pg.event;

public interface NoArgsEvent {
    void addListener(NoArgsEventReceiver listener);
    void removeListener(NoArgsEventReceiver listener);
    void invoke();
    void removeAllListeners();
}
