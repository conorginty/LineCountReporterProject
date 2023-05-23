package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.model.ReportItem;
import org.linecountreporter.report.reporter.LineCountReporter;

import java.util.List;

public interface ReportGenerator {
    Object generate(LineCountReporter lineCountReporter, ReportItemsFilter reportItemsFilter);

    default LineCountReportBody generateLineCountReportBody(LineCountReporter lineCountReporter, ReportItemsFilter reportItemsFilter) {
        LineCountReportBody lineCountReportBody = lineCountReporter.generateReport();
        List<ReportItem> reportItems = lineCountReportBody.getReportItems();

        List<ReportItem> filteredItems = reportItemsFilter.filterReportItems(reportItems);
        lineCountReportBody.setReportItems(filteredItems);
        return lineCountReportBody;
    }
}
