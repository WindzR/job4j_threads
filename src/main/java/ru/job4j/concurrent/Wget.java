package ru.job4j.concurrent;

public class Wget {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rMineCraft version 0.01  ");
                            System.out.print("Loading : " + i  + "%");
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
