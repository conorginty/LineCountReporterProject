package org.linecountreporter.report.reporter;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileCollector;
import org.linecountreporter.utils.Utils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineCountReporterTest {
    private static final String LINE_COUNT_REPORTER_DIRECTORY_PATH = Utils.RESOURCES_DIRECTORY_PATH + "/line-count-reporter";

    @Test
    void should_give_accurate_line_count_report() {
        Map<String, Long> expected = Map.of(
            "file1.txt", 6L,
            "file2.txt", 7L
        );

        FileCollector fileCollector = new FileCollector(LINE_COUNT_REPORTER_DIRECTORY_PATH);
        LineCountReporter lineCountReporter = new LineCountReporter(fileCollector);
        Map<String, Long> actual = lineCountReporter.getReport();

        assertEquals(expected, actual);
    }
}