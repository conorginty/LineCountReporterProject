package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.reporter.LineCountReporter;

public class SingleReportGenerator implements ReportGenerator {
    @Override
    public LineCountReportBody generate(LineCountReporter lineCountReporter, ReportItemsFilter reportItemsFilter) {
        return generateLineCountReportBody(lineCountReporter, reportItemsFilter);
    }
}
