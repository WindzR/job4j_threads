package ru.job4j.waitNotify;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOfferValuesProducerWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
        }, "producer");
        producer.start();
        Thread.sleep(500);
        assertThat(producer.getState(), is(Thread.State.WAITING));
    }

    @Test
    public void whenPollValuesConsumerWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread consumer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            queue.poll();
        }, "consumer");
        consumer.start();
        Thread.sleep(500);
        assertThat(consumer.getState(), is(Thread.State.WAITING));
    }

    @Test
    public void whenProduceValuesThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
        }, "producer");
        Thread consumer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            queue.poll();
            queue.poll();
            queue.poll();
        }, "consumer");
        producer.start();
        consumer.start();
        Thread.sleep(1000);
        assertThat(queue.size(), is(0));
    }
}