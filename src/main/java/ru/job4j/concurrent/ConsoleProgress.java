package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        final String[] SYMBOLS = {"\\", "|", "/"};
        try {
            int count = 0;
            int i = 0;
            while (!Thread.currentThread().isInterrupted() && count < 100) {
                System.out.print("\r Loading..." + SYMBOLS[i++]);
                if (i == SYMBOLS.length) {
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
        Thread.sleep(3000); /* симулируем выполнение параллельной задачи в течение 3 секунд. */
        progress.interrupt();
    }
}
