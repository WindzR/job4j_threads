package ru.job4j.cas;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrementByOne() {
        CASCount counter = new CASCount();
        counter.increment();
        assertThat(counter.get(), is(1));
    }

    @Test
    public void whenNotIncrement() {
        CASCount counter = new CASCount();
        assertThat(counter.get(), is(0));
    }

    @Test
    public void whenThreadsIncrement() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                counter.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(counter.get(), is(7));
    }
}