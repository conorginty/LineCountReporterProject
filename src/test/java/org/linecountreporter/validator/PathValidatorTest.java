package org.linecountreporter.validator;

import org.junit.jupiter.api.Test;
import org.linecountreporter.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PathValidatorTest {
    private static final Path DIRECTORY_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/validation");
    private static final Path FILE_PATH = Path.of(DIRECTORY_PATH + "/text.txt");

    @Test
    void given_a_valid_file_path_then_no_exception_thrown() {
        PathValidator.validateFile(FILE_PATH);
    }

    @Test
    void given_a_valid_directory_path_then_no_exception_thrown() {
        PathValidator.validateDirectory(DIRECTORY_PATH);
    }

    @Test
    void given_a_directory_when_file_required_then_exception_thrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> PathValidator.validateFile(DIRECTORY_PATH));
        assertEquals(String.format("Supplied path: %s is not a file", DIRECTORY_PATH), exception.getMessage());
    }

    @Test
    void given_a_valid_but_unreadable_file_then_exception_is_thrown() {
        FILE_PATH.toFile().setReadable(false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> PathValidator.validateFile(FILE_PATH));
        assertEquals(String.format("Supplied file: %s is not readable", FILE_PATH), exception.getMessage());
    }

    @Test
    void given_a_file_when_directory_required_then_exception_thrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> PathValidator.validateDirectory(FILE_PATH));
        assertEquals(String.format("Supplied path: %s is not a directory", FILE_PATH), exception.getMessage());
    }

    @Test
    void given_a_valid_but_unreadable_directory_then_exception_is_thrown() throws IOException {
        DIRECTORY_PATH.toFile().setReadable(false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> PathValidator.validateDirectory(DIRECTORY_PATH));
        assertEquals(String.format("Supplied directory: %s is not readable", DIRECTORY_PATH), exception.getMessage());
    }

    @Test
    void given_an_invalid_path_then_throw_exception() {
        Path invalidPath = Path.of("i/don't/exist");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> PathValidator.validateFile(invalidPath));
        assertEquals(String.format("Supplied path: %s does not exist", invalidPath), exception.getMessage());
    }
}