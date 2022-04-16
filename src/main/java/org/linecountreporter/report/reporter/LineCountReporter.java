package org.linecountreporter.report.reporter;

import com.google.common.annotations.VisibleForTesting;
import org.linecountreporter.file.collector.FileCollector;
import org.linecountreporter.file.counter.FileLineCounter;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LineCountReporter {
    private Map<String, Long> report = new LinkedHashMap<>();
    private FileCollector fileCollector;
    private String path;

    public LineCountReporter(FileCollector fileCollector) {
        this.fileCollector = fileCollector;
        generateReport();
        this.path = fileCollector.getPath();
    }

    @VisibleForTesting
    void generateReport() {
        List<String> filenames = this.fileCollector.getFilenames();
        for (String filename: filenames) {
            String path = generatePathToFile(this.fileCollector.getPath(), filename);
            long lineCount = FileLineCounter.lineCount(Path.of(path));
            this.report.put(filename, lineCount);
        }
    }

    private String generatePathToFile(String directoryPath, String filename) {
        if (directoryPath.endsWith("/")) {
            return directoryPath + filename;
        }
        return directoryPath + "/" + filename;
    }

    public Map<String, Long> getReport() {
        return this.report;
    }

    public FileCollector getFileCollector() {
        return this.fileCollector;
    }

    public String getPath() {
        return this.path;
    }
}
