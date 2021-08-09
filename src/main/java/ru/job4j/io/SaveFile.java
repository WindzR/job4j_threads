package ru.job4j.io;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(final File file) {
        this.file = file;
    }

    /**
     * метод вместо saveContent(String content) в классе ParseFile
     */
    public synchronized void saveContent(String content) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
