package org.linecountreporter.file.counter;

import org.linecountreporter.validator.PathValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileLineCounter {
    public static long lineCount(Path path) throws IOException {
        PathValidator.validateFile(path);
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            return stream.count();
        } catch (IOException e) {
            throw new IOException("Something went wrong while the lines were counted", e);
        }
    }
}
