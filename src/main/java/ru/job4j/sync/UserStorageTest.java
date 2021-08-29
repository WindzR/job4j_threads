package ru.job4j.sync;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class UserStorageTest {

    @Test
    public void whenAddUser() {
        UserStorage storage = new UserStorage();
        User user = new User(1, 200);
        assertTrue(storage.add(user));
    }

    @Test
    public void whenUpdateUser() {
        UserStorage storage = new UserStorage();
        User user = new User(1, 200);
        assertTrue(storage.add(user));
        user.setAmount(150);
        assertTrue(storage.update(user));
    }

    @Test
    public void whenDeleteUser() {
        UserStorage storage = new UserStorage();
        User user = new User(1, 200);
        assertTrue(storage.add(user));
        assertTrue(storage.delete(user));
    }

    @Test
    public void whenTransferBetweenUsers() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 50);
        User user2 = new User(2, 100);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 50);
        assertThat(user1.getAmount(), is(0));
        assertThat(user2.getAmount(), is(150));
    }

    @Test
    public void whenErrorTransfer() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 50);
        User user2 = new User(2, 100);
        storage.add(user1);
        storage.add(user2);
        assertFalse(storage.transfer(1, 2, 100));
    }
}