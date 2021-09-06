package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        boolean result = false;
        User source = store.get(fromId);
        User destination = store.get(toId);
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException(String.format("User with id %d does not exist!", fromId));
        }
        if (Objects.isNull(destination)) {
            throw new IllegalArgumentException(String.format("User with id %d does not exist!", toId));
        }
        if (source.getAmount() >= amount) {
            source.setAmount(source.getAmount() - amount);
            destination.setAmount(destination.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
