package org.linecountreporter.report.reporter;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.model.ReportItem;
import org.linecountreporter.utils.Utils;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LineCountReporterTestBody {
    private static final Path LINE_COUNT_REPORTER_DIRECTORY_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/line-count-reporter");

    @Test
    void should_give_accurate_line_count_report() {
        List<ReportItem> expected = List.of(
            new ReportItem("file1.txt", 6L),
            new ReportItem("file2.txt", 7L)
        );

        FileEntityCollector fileEntityCollector = new FileEntityCollector(LINE_COUNT_REPORTER_DIRECTORY_PATH);
        LineCountReporter lineCountReporter = new LineCountReporter(fileEntityCollector);
        LineCountReportBody lineCountReportBody = lineCountReporter.generateReport();
        List<ReportItem> actual = lineCountReportBody.getReportItems();

        assertTrue(actual.containsAll(expected));
    }
}