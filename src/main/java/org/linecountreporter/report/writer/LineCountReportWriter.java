package org.linecountreporter.report.writer;

import org.linecountreporter.report.reporter.LineCountReporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LineCountReportWriter implements ReportWriter {
    private String title;
    private LineCountReporter lineCounterReporter;
    private String directoryPath;

    public LineCountReportWriter(String title, LineCountReporter lineCounterReporter) {
        this.title = title;
        this.lineCounterReporter = lineCounterReporter;
        this.directoryPath = lineCounterReporter.getPath();
    }

    @Override
    public void writeReport() {
        Map<String, Long> report = lineCounterReporter.getReport();
        String outFile = this.directoryPath + "/output.txt";

        try (PrintWriter writer = new PrintWriter(outFile, StandardCharsets.UTF_8)) {
            writeHeader(writer);
            writePathInformation(writer);
            for (var entry : report.entrySet()) {
                String result = "- " + entry.getKey() + " => " + entry.getValue();
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
