package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    /**
     * метод вместо getContent() и getContentWithoutUnicode()
     */
    public String getContentByPredicate(Predicate<Character> filter) {
        String output = "";
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))){
            output = new ContentFile().fileContent(filter, in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output;
    }
}
