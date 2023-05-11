package org.linecountreporter.file.counter;

import org.junit.jupiter.api.Test;
import org.linecountreporter.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileLineCounterTest {
    @Test
    void can_get_line_count() throws IOException {
        String filepath = Utils.RESOURCES_DIRECTORY_PATH + "/file-line-counter/test.txt";

        long lineCount = FileLineCounter.lineCount(Path.of(filepath));

        assertEquals(9, lineCount);
    }
}