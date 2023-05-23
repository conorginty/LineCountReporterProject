package org.linecountreporter.report.model;

import java.nio.file.Path;
import java.util.List;

public class LineCountReportBody {
    private final Path sourceDirectory;
    private List<ReportItem> reportItems;

    public LineCountReportBody(Path sourceDirectory, List<ReportItem> reportItems) {
        this.sourceDirectory = sourceDirectory;
        this.reportItems = reportItems;
    }

    public Path getPath() {
        return sourceDirectory;
    }

    public List<ReportItem> getReportItems() {
        return reportItems;
    }

    public void setReportItems(List<ReportItem> reportItems) {
        this.reportItems = reportItems;
    }

    @Override
    public String toString() {
        return new ReportBodyOutput(sourceDirectory, reportItems).toString();
    }
}
