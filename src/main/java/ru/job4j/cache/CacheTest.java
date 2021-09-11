package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddValue() {
        Cache cache = new Cache();
        Base value = new Base(1, 1);
        value.setName("Test");
        assertTrue(cache.add(value));
    }

    @Test
    public void whenSuccessUpdate() {
        Cache cache = new Cache();
        Base value1 = new Base(1, 1);
        Base value2 = new Base(1, 1);
        value2.setName("Test");
        assertTrue(cache.add(value1));
        assertTrue(cache.update(value2));
    }

    @Test(expected = OptimisticException.class)
    public void whenNotUpdate() {
        Cache cache = new Cache();
        Base value1 = new Base(1, 1);
        Base value2 = new Base(1, 2);
        value2.setName("Test");
        cache.add(value1);
        cache.update(value2);
    }

    @Test
    public void whenDeleteValue() {
        Cache cache = new Cache();
        Base value = new Base(1, 1);
        cache.add(value);
        cache.delete(value);
        assertTrue(cache.add(value));
    }
}