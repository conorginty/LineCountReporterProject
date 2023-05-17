package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.LineCountReport;
import org.linecountreporter.report.model.ReportItem;
import org.linecountreporter.report.reporter.LineCountReporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class LineCountReportWriter {

    private final Path directoryPath;
    private final LineCountReporter lineCounterReporter;
    private final ReportItemsFilter reportItemsFilter;
    private final String title = "THE REPORT TITLE";

    public LineCountReportWriter(LineCountReporter lineCounterReporter) {
        this.lineCounterReporter = lineCounterReporter;
        this.directoryPath = lineCounterReporter.getPath();
        Properties config = ConfigurationReader.readConfiguration();
        Options options = new Options(config);
        this.reportItemsFilter = new ReportItemsFilter(options);
    }

    public void writeReport() {
        LineCountReport lineCountReport = lineCounterReporter.generateReport();
        List<ReportItem> reportItems = lineCountReport.getReportItems();

        List<ReportItem> filteredItems = reportItemsFilter.filterReportItems(reportItems);

        String outFile = this.directoryPath + "/output.txt";

        try (PrintWriter writer = new PrintWriter(outFile, StandardCharsets.UTF_8)) {
            writeHeader(writer);
            writePathInformation(writer);
            for (ReportItem entry : filteredItems) {
                String result = "- " + entry.getFilename() + " => " + entry.getLineCount();
//              TODO: FIX - result += resultIfLineCountMeasureIsSet(entry.getLineCount());
                writer.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePathInformation(PrintWriter writer) {
        writer.println();
        writer.println("Path: " + lineCounterReporter.getFileCollector().getPath());
        writer.println();
    }

    private void writeHeader(PrintWriter writer) {
        String titleWrapper = generateTitleWrapper();
        writer.println(titleWrapper);
        writer.println(this.title);
        writer.println(titleWrapper);
    }

    private String generateTitleWrapper() {
        StringBuilder output = new StringBuilder();
        for (int i=0; i < this.title.length(); i++) {
            output.append("*");
        }
        return String.valueOf(output);
    }
}
