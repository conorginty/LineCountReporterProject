package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.ReportItem;

import java.util.List;
import java.util.stream.Collectors;

public class ReportItemsFilter {

    private final ReportOptions reportOptions;

    public ReportItemsFilter(ReportOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public List<ReportItem> filterReportItems(List<ReportItem> reportItems) {
        return reportItems.stream()
            .filter(reportOptions.fileTypeFilter())
            .filter(reportOptions.limitLineCountFilter())
            .collect(Collectors.toList());
    }
}
