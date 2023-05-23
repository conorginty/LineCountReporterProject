package org.linecountreporter.report.model;

import java.nio.file.Path;
import java.util.List;

public class ReportBodyOutput {
    private final Path sourceDirectory;
    private final List<ReportItem> reportItems;

    public ReportBodyOutput(Path sourceDirectory, List<ReportItem> reportItems) {
        this.sourceDirectory = sourceDirectory;
        this.reportItems = reportItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        createPathInformation(sb);
        createReportItems(sb);
        sb.append("======================\n\n");
        return sb.toString();
    }

    private void createPathInformation(StringBuilder sb) {
        sb.append("Path: " + sourceDirectory);
        sb.append("\n");
        sb.append("\n");
    }

    private void createReportItems(StringBuilder sb) {
        for (ReportItem item: reportItems) {
            sb.append("- " + item);
            sb.append("\n");
        }
    }
}
