package org.linecountreporter.report.reporter;

import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.file.FileLineCounter;
import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.model.ReportItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LineCountReporter {
    private final FileEntityCollector fileEntityCollector;
    private final Path path;

    public LineCountReporter(FileEntityCollector fileEntityCollector) {
        this.fileEntityCollector = fileEntityCollector;
        this.path = fileEntityCollector.getPath();
    }

    public LineCountReportBody generateReport() {
        List<String> filenames = fileEntityCollector.getFilenames();
        List<ReportItem> reportItems = filenames.stream()
            .map(filename -> {
                Path filePath = this.path.resolve(filename);
                try {
                    long lineCount = FileLineCounter.lineCount(filePath);
                    return new ReportItem(filename, lineCount);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to count lines in file: " + filePath, e);
                }
            }).collect(Collectors.toList());

        return new LineCountReportBody(fileEntityCollector.getPath(), reportItems);
    }

    public FileEntityCollector getFileEntityCollector() {
        return fileEntityCollector;
    }

    public Path getPath() {
        return this.path;
    }
}
