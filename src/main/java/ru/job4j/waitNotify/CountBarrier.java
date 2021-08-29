package ru.job4j.waitNotify;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            while (count != total) {
                System.out.println("count " + Thread.currentThread().getName());
                count++;
                monitor.notifyAll();
            }
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    System.out.println("await " + Thread.currentThread().getName());
                    monitor.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(7);
        Thread master = new Thread(() -> {
            barrier.count();
            System.out.println(Thread.currentThread().getName() +  " working");
        }, "master");
        Thread slave = new Thread(() -> {
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " working");
        }, "slave");
        master.start();
        slave.start();
    }
}
