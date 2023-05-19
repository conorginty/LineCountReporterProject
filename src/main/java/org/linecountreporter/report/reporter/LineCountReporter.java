package org.linecountreporter.report.reporter;

import org.linecountreporter.file.FileCollector;
import org.linecountreporter.file.FileLineCounter;
import org.linecountreporter.report.model.LineCountReport;
import org.linecountreporter.report.model.ReportItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LineCountReporter {
    private final FileCollector fileCollector;
    private final Path path;

    public LineCountReporter(FileCollector fileCollector) {
        this.fileCollector = fileCollector;
        this.path = fileCollector.getPath();
    }

    public LineCountReport generateReport() {
        List<String> filenames = fileCollector.getFilenames();
        List<ReportItem> report = filenames.stream()
            .map(filename -> {
                Path filePath = this.path.resolve(filename);
                try {
                    long lineCount = FileLineCounter.lineCount(filePath);
                    return new ReportItem(filename, lineCount);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to count lines in file: " + filePath, e);
                }
            }).collect(Collectors.toList());

        return new LineCountReport(fileCollector.getPath(), report);
    }

    public FileCollector getFileCollector() {
        return this.fileCollector;
    }

    public Path getPath() {
        return this.path;
    }
}
