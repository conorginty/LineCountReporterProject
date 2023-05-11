package org.linecountreporter.report.reporter;

import com.google.common.annotations.VisibleForTesting;
import org.linecountreporter.file.collector.FileCollector;
import org.linecountreporter.file.counter.FileLineCounter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LineCountReporter {
    private final Map<String, Long> report = new LinkedHashMap<>();
    private final FileCollector fileCollector;
    private final Path path;

    public LineCountReporter(FileCollector fileCollector) {
        this.fileCollector = fileCollector;
        this.path = fileCollector.getPath();
        generateReport();
    }

    @VisibleForTesting
    void generateReport() {
        List<String> filenames = this.fileCollector.getFilenames();
        filenames.stream()
            .map(path::resolve)
            .forEach(path -> {
                try {
                    long lineCount = FileLineCounter.lineCount(path);
                    this.report.put(path.getFileName().toString(), lineCount);
                } catch (IOException e) {
                    throw new RuntimeException("Something went wrong while the lines were counted", e);
                }
            });
    }

    public FileCollector getFileCollector() {
        return this.fileCollector;
    }

    public Path getPath() {
        return this.path;
    }

    public Map<String, Long> getReport() {
        return this.report;
    }
}
