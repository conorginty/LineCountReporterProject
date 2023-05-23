package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.reporter.LineCountReporter;
import org.linecountreporter.utils.Utils;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecursiveReportGeneratorTest {
    private static final Path ROOT_DIR = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/recursive-report-generator");

    @Test
    public void given_a_filepath_collect_all_files_recursively() {
        FileEntityCollector fileEntityCollector = new FileEntityCollector(ROOT_DIR);
        LineCountReporter lineCountReporter = new LineCountReporter(fileEntityCollector);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(newReportOptions());

        RecursiveReportGenerator reportGenerator = new RecursiveReportGenerator();
        List<LineCountReportBody> lineCountReportBodies = reportGenerator.generate(lineCountReporter, reportItemsFilter);

        assertEquals(5, lineCountReportBodies.size());

        int totalFiles = 0;
        for (LineCountReportBody body: lineCountReportBodies) {
            totalFiles += body.getReportItems().size();
        }

        assertEquals(8, totalFiles);
    }

    private ReportOptions newReportOptions() {
        ReportOptions reportOptions = mock(ReportOptions.class);
        Predicate predicate = mock(Predicate.class);
        when(predicate.test(any())).thenReturn(true);
        when(reportOptions.fileTypeFilter()).thenReturn(predicate);
        when(reportOptions.limitLineCountFilter()).thenReturn(predicate);
        return reportOptions;
    }
}