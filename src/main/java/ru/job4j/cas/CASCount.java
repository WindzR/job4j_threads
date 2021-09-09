package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        //count.getAndUpdate(v -> v + 1);
        int temp;
        int value;
        do {
            value = count.get();
            temp = value + 1;
        } while (!count.compareAndSet(value, temp));
    }

    public int get() {
        return count.get();
    }
}
