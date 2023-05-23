package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.model.ReportItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportItemsFilterTest {
    private final List<ReportItem> REPORT_ITEMS = List.of(
        new ReportItem("text1.txt", 5),
        new ReportItem("text2.txt", 15),
        new ReportItem("python.py", 10)
    );

    @Test
    public void filter_out_items_whose_filenames_dont_end_with_inputted_file_type() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType("txt");

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(2, filteredReportItems.size());

        assertTrue(filteredReportItems.contains(new ReportItem("text1.txt", 5)));
        assertTrue(filteredReportItems.contains(new ReportItem("text2.txt", 15)));

        assertFalse(filteredReportItems.contains(new ReportItem("python.py", 10)));
    }

    @Test
    public void filter_out_items_whose_line_counts_exceed_the_inputted_line_count_limit() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setLineCountLimit("10");

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(2, filteredReportItems.size());

        assertTrue(filteredReportItems.contains(new ReportItem("text1.txt", 5)));
        assertTrue(filteredReportItems.contains(new ReportItem("python.py", 10)));

        assertFalse(filteredReportItems.contains(new ReportItem("text2.txt", 15)));
    }

    @Test
    public void filter_out_items_that_dont_match_all_predicate_requirements() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType("txt");
        reportArguments.setLineCountLimit("10");

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(1, filteredReportItems.size());

        assertEquals(new ReportItem("text1.txt", 5), filteredReportItems.get(0));
    }

    @Test
    public void given_no_filters_set_then_dont_filter_out_any_items() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType(null);
        reportArguments.setLineCountLimit(null);

        ReportOptions reportOptions = new ReportOptions(reportArguments);
        ReportItemsFilter reportItemsFilter = new ReportItemsFilter(reportOptions);

        List<ReportItem> filteredReportItems = reportItemsFilter.filterReportItems(REPORT_ITEMS);
        assertEquals(3, filteredReportItems.size());

        assertTrue(filteredReportItems.containsAll(REPORT_ITEMS));
    }
}