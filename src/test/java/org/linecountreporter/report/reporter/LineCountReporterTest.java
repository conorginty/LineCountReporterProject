package org.linecountreporter.report.reporter;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.FileCollector;
import org.linecountreporter.report.model.LineCountReport;
import org.linecountreporter.report.model.ReportItem;
import org.linecountreporter.utils.Utils;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineCountReporterTest {
    private static final Path LINE_COUNT_REPORTER_DIRECTORY_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/line-count-reporter");

    @Test
    void should_give_accurate_line_count_report() {
        List<ReportItem> expected = List.of(
            new ReportItem("file1.txt", 6L),
            new ReportItem("file2.txt", 7L)
        );

        FileCollector fileCollector = new FileCollector(LINE_COUNT_REPORTER_DIRECTORY_PATH);
        LineCountReporter lineCountReporter = new LineCountReporter(fileCollector);
        LineCountReport lineCountReport = lineCountReporter.generateReport();
        List<ReportItem> actual = lineCountReport.getReportItems();

        assertEquals(expected, actual);
    }
}