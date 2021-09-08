package ru.job4j.waitNotify;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOfferValuesProducerWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            try {
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "producer");
        Thread consumer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                queue.poll();
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer");
        producer.start();
        consumer.start();
        Thread.sleep(1000);
        assertThat(queue.size(), is(0));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}