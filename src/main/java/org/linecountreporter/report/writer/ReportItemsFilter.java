package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.ReportItem;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportItemsFilter {

    private final Options options;

    public ReportItemsFilter(Options options) {
        this.options = options;
    }

    public List<ReportItem> filterReportItems(List<ReportItem> reportItems) {
        List<Predicate<ReportItem>> filters = options.getFilters();

        return reportItems.stream()
            .filter(item -> applyFilters(item, filters))
            .collect(Collectors.toList());
    }

    private boolean applyFilters(ReportItem item, List<Predicate<ReportItem>> filters) {
        for (Predicate<ReportItem> filter : filters) {
            if (filter.test(item)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
