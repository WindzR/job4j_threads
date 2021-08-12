package ru.job4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ContentFile {

    public String fileContent(Predicate<Character> filter, InputStream in) throws IOException {
        StringBuffer output = new StringBuffer();
        byte[] buffer = new byte[1024];
        synchronized (this) {
            int data;
            while ((data = in.read(buffer, 0, 1024)) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
