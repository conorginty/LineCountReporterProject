package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.model.ReportItem;
import org.linecountreporter.report.reporter.LineCountReporter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SingleReportGeneratorTest {
    private static final List<ReportItem> REPORT_ITEMS = List.of(
        new ReportItem("file1", 5),
        new ReportItem("file2", 10)
    );

    @Test
    public void generate_report_of_all_files_in_a_directory() {
        SingleReportGenerator reportGenerator = new SingleReportGenerator();
        LineCountReporter lineCountReporter = newLineCountReporter();
        ReportItemsFilter reportItemsFilter = newReportItemsFilter();
        LineCountReportBody lineCountReportBody = reportGenerator.generate(lineCountReporter, reportItemsFilter);
        assertEquals(REPORT_ITEMS, lineCountReportBody.getReportItems());
    }

    private LineCountReporter newLineCountReporter() {
        LineCountReporter lineCountReporter = mock(LineCountReporter.class);
        when(lineCountReporter.generateReport()).thenReturn(
            new LineCountReportBody(null, REPORT_ITEMS));
        return lineCountReporter;
    }

    private ReportItemsFilter newReportItemsFilter() {
        ReportItemsFilter reportItemsFilter = mock(ReportItemsFilter.class);
        when(reportItemsFilter.filterReportItems(anyList())).thenReturn(REPORT_ITEMS);
        return reportItemsFilter;
    }
}