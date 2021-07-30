package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetFile implements Runnable {
    private final String url;
    private final int speed;

    public WgetFile(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public String fileName() {
        String[] path = url.split("/");
        String sourceName = path[path.length - 1];
        String[] source = sourceName.split("\\.");
        return source[0] + "_tmp." + source[1];
    }

    @Override
    public void run() {
        /* Скачать файл*/
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(fileName())) {
            byte[] dataBuffer = new byte[speed * 1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed * 1024)) != -1) {
                long finish = System.currentTimeMillis();
                System.out.println("Time of download cluster --> "+ (finish - start));
                long difference = 1000 - (finish - start);
//                System.out.println(difference);
                if (finish - start < 1000) {
                    Thread.sleep(difference);
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Enter URL of downloading file & speed of downloading");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetFile(url, speed));
        wget.start();
        wget.join();
    }
}
