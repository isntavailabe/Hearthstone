package client.state.game.objectPool;

import java.util.HashSet;
import java.util.Set;

public abstract class ObjectPool<T> {
    private Set<T> available;

    ObjectPool() {
        available = new HashSet<>();
    }

    protected abstract T create();


    T checkOut() {
        if (available.isEmpty()) {
            available.add(create());
        }
        T instance = available.iterator().next();
        available.remove(instance);
        return instance;
    }

    public void checkIn(T instance) {
        available.add(instance);
    }
}
