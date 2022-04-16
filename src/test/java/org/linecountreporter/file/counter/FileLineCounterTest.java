package org.linecountreporter.file.counter;

import org.junit.jupiter.api.Test;
import org.linecountreporter.utils.Utils;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileLineCounterTest {
    @Test
    void can_get_line_count() {
        String FILE_LINE_DIRECTORY_PATH = Utils.RESOURCES_DIRECTORY_PATH + "/file-line-counter";
        String filepath =  FILE_LINE_DIRECTORY_PATH + "/test.txt";

        long lineCount = FileLineCounter.lineCount(Path.of(filepath));

        assertEquals(9, lineCount);
    }
}