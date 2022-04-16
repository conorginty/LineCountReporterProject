package org.linecountreporter.file.counter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileLineCounter {
    public static long lineCount(Path path) {
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            return stream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
