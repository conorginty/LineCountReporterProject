package org.linecountreporter.report.model;

import java.nio.file.Path;
import java.util.List;

public class LineCountReport {
    private final Path sourceDirectory;
    private final List<ReportItem> reportItems;

    public LineCountReport(Path sourceDirectory, List<ReportItem> reportItems) {
        this.sourceDirectory = sourceDirectory;
        this.reportItems = reportItems;
    }

    public Path getPath() {
        return sourceDirectory;
    }

    public List<ReportItem> getReportItems() {
        return reportItems;
    }
}
