package org.linecountreporter.report.writer;

import org.linecountreporter.report.reporter.LineCountReporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LineCountReportWriter implements ReportWriter {
    public static final long DEFAULT_LINE_COUNT_LIMIT = 0L;
    private Options options;
    private LineCountReporter lineCounterReporter;
    private String directoryPath;
    private String title;
    private String fileType;
    private long lineCountLimit;

    public LineCountReportWriter(Options options, LineCountReporter lineCounterReporter) {
        this.options = options;
        this.lineCounterReporter = lineCounterReporter;
        this.directoryPath = lineCounterReporter.getPath();
        this.title = options.getTitleOrDefault();
        this.fileType = options.getFileTypeOrDefault();
        this.lineCountLimit = options.getLineCountLimit();
    }

    @Override
    public void writeReport() {
        Map<String, Long> report = lineCounterReporter.getReport();

        filterReportOnFileType(report);
        filterReportOnLineCountLimit(report);

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

    private void filterReportOnFileType(Map<String, Long> report) {
        Set<String> filesToRemoveFromReport = new HashSet<>();
        for (String filename: report.keySet()) {
            String filetype = options.getFileTypeOrDefault();
            if (!filename.endsWith(filetype.toLowerCase())) {
                filesToRemoveFromReport.add(filename);
            }
        }

        report.keySet().removeAll(filesToRemoveFromReport);
    }

    private void filterReportOnLineCountLimit(Map<String, Long> report) {
        if (lineCountLimit != DEFAULT_LINE_COUNT_LIMIT) {
            Set<String> filesToRemoveFromReport = new HashSet<>();
            for (String filename: report.keySet()) {
                long limit = options.getLineCountLimit();
                long fileLineCount = report.get(filename);
                if (fileLineCount > limit) {
                    filesToRemoveFromReport.add(filename);
                }
            }

            report.keySet().removeAll(filesToRemoveFromReport);
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
