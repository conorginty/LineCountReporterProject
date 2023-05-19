package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.model.ReportItem;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class ReportOptionsTest {

    @Test
    public void given_file_type_then_predicate_filters_out_items_not_ending_in_file_type() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType("a");
        ReportOptions reportOptions = new ReportOptions(reportArguments);

        Predicate<ReportItem> fileTypePredicate = reportOptions.fileTypeFilter();
        assertTrue(fileTypePredicate.test(new ReportItem("ends_with_a", 0)));
        assertFalse(fileTypePredicate.test(new ReportItem("ends_with_b", 0)));
    }

    @Test
    public void given_null_file_type_then_filter_isnt_used() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setFileType(null);
        ReportOptions reportOptions = new ReportOptions(reportArguments);

        Predicate<ReportItem> fileTypePredicate = reportOptions.fileTypeFilter();

        assertTrue(fileTypePredicate.test(new ReportItem("ends_with_a", 0)));
        assertTrue(fileTypePredicate.test(new ReportItem("ends_with_b", 0)));
    }

    @Test
    public void given_line_count_limit_then_predicate_filters_out_items_with_limit_above_the_one_specified() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setLineCountLimit("10");
        ReportOptions reportOptions = new ReportOptions(reportArguments);

        Predicate<ReportItem> limitLineCountPredicate = reportOptions.limitLineCountFilter();
        assertTrue(limitLineCountPredicate.test(new ReportItem("less_than_10_lines_long", 0)));
        assertFalse(limitLineCountPredicate.test(new ReportItem("more_than_10_lines_long", 20)));
    }

    @Test
    public void given_invalid_line_count_limit_type_throws_exception() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setLineCountLimit("Not a Number");
        ReportOptions reportOptions = new ReportOptions(reportArguments);

        Exception exception = assertThrows(IllegalArgumentException.class, reportOptions::limitLineCountFilter);
        assertEquals("Invalid lineCountLimit value: Not a Number", exception.getMessage());
    }

    @Test
    public void given_null_line_count_limit_then_filter_isnt_used() {
        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setLineCountLimit(null);
        ReportOptions reportOptions = new ReportOptions(reportArguments);

        Predicate<ReportItem> limitLineCountPredicate = reportOptions.limitLineCountFilter();

        assertTrue(limitLineCountPredicate.test(new ReportItem("less_than_10_lines_long", 0)));
        assertTrue(limitLineCountPredicate.test(new ReportItem("more_than_10_lines_long", 20)));
    }
}