package pg.proj.pg.event.entity.impl;

import pg.proj.pg.event.entity.api.OneArgEvent;
import pg.proj.pg.event.receiver.api.OneArgEventReceiver;

import java.util.ArrayList;
import java.util.Iterator;

public class OneArgEventImpl<T> implements OneArgEvent<T> {

    private final ArrayList<OneArgEventReceiver<T>> listeners;

    private final ArrayList<OneArgEventReceiver<T>> listenersToRemove;

    public OneArgEventImpl() {
        listeners = new ArrayList<>();
        listenersToRemove = new ArrayList<>();
    }

    @Override
    public void addListener(OneArgEventReceiver<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(OneArgEventReceiver<T> listener) {
        try {
            listeners.remove(listener);
        } catch (Exception err) {
            listenersToRemove.add(listener);
        }
    }

    @Override
    public void invoke(T arg) {
        for (var listener : listeners) {
            listener.onOneArgEventOccurred(arg);
        }
        clearListeners();
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
        listenersToRemove.clear();
    }

    private void clearListeners() {
        Iterator<OneArgEventReceiver<T>> iterator = listenersToRemove.iterator();
        while (iterator.hasNext()) {
            OneArgEventReceiver<T> listener = iterator.next();
            listeners.remove(listener);
            iterator.remove();
        }
    }
}
