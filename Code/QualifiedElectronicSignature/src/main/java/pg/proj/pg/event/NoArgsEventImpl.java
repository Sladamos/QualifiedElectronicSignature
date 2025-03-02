package pg.proj.pg.event;

import java.util.ArrayList;
import java.util.Iterator;

public class NoArgsEventImpl implements NoArgsEvent {

    private final ArrayList<NoArgsEventReceiver> listeners;

    private final ArrayList<NoArgsEventReceiver> listenersToRemove;

    public NoArgsEventImpl() {
        listeners = new ArrayList<>();
        listenersToRemove = new ArrayList<>();
    }

    @Override
    public void addListener(NoArgsEventReceiver listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(NoArgsEventReceiver listener) {
        try {
            listeners.remove(listener);
        } catch (Exception err) {
            listenersToRemove.add(listener);
        }
    }

    @Override
    public void invoke() {
        for (var listener : listeners) {
            listener.onNoArgsEventOccurred();
        }
        clearListeners();
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
        listenersToRemove.clear();
    }

    private void clearListeners() {
        Iterator<NoArgsEventReceiver> iterator = listenersToRemove.iterator();
        while (iterator.hasNext()) {
            NoArgsEventReceiver listener = iterator.next();
            listeners.remove(listener);
            iterator.remove();
        }
    }
}
