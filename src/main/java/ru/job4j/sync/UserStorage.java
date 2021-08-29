package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> store = new HashMap<>();

    public  synchronized boolean add(User user) {
        boolean result = false;
        if (!store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        if (store.containsKey(user.getId())) {
            store.remove(user.getId());
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        UserStorage storage = new UserStorage();
        boolean result = false;
        User source = store.get(fromId);
        User destination = store.get(toId);
        if(source.getAmount() >= amount) {
            source.setAmount(source.getAmount() - amount);
            destination.setAmount(destination.getAmount() + amount);
            storage.update(source);
            storage.update(destination);
            result = true;
        }
        return result;
    }
}
