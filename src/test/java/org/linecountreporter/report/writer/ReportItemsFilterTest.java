package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.model.ReportItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportItemsFilterTest {
    private final List<ReportItem> REPORT_ITEMS = List.of(
        new ReportItem("text1.txt", 5),
        new ReportItem("text2.txt", 15),
        new ReportItem("python.py", 10)
    );

    @Test
    public void x() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType("txt");
        reportArguments.setLineCountLimit("10");

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(1, filteredReportItems.size());
    }

    @Test
    public void y() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType("py");
        reportArguments.setLineCountLimit("1000");

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(1, filteredReportItems.size());
    }

    @Test
    public void invalid() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType(null);
        reportArguments.setLineCountLimit(null);

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(3, filteredReportItems.size());
    }
}