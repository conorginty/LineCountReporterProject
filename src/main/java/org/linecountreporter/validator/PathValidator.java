package org.linecountreporter.validator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator {
    public static void validateFile(Path path) {
        pathExists(path);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Supplied path: " + path + " is not a file");
        }

        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Supplied file: " + path + " is not readable");
        }
    }

    public static void validateDirectory(Path path) {
        pathExists(path);
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Supplied path: " + path + " is not a directory");
        }

        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Supplied directory: " + path + " is not readable");
        }
    }

    private static void pathExists(Path input) {
        File path = new File(input.toString());
        if (!path.exists()) {
            throw new IllegalArgumentException("Supplied path: " + path + " does not exist");
        }
    }
}
