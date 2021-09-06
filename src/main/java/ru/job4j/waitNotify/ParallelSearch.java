package ru.job4j.waitNotify;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
        final Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    try {
                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, "producer"
        );
        final Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " start");
                    while (!producer.isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
    }
}
