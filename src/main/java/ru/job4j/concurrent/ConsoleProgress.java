package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private final String[] SYMBOLS = {"\\", "|", "/"};

    @Override
    public void run() {
        try {
            int count = 0;
            int i = 0;
            while (!Thread.currentThread().isInterrupted() && count < 100) {
                System.out.print("\r Loading..." + SYMBOLS[i++]);
                if (i == 3) {
                    i = 0;
                }
                count++;
                Thread.sleep(500);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
