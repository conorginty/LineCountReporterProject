package org.linecountreporter.report.writer;

import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.reporter.LineCountReporter;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecursiveReportGenerator implements ReportGenerator {
    private final List<LineCountReportBody> lineCountReportBodies = new ArrayList<>();

    @Override
    public List<LineCountReportBody> generate(LineCountReporter lineCountReporter, ReportItemsFilter reportItemsFilter) {
        lineCountReportBodies.add(generateLineCountReportBody(lineCountReporter, reportItemsFilter));

        List<File> directories = lineCountReporter.getFileEntityCollector().getDirectories();
        for (File directory: directories) {
            FileEntityCollector fileEntityCollector = new FileEntityCollector(Path.of(directory.getPath()));
            lineCountReporter = new LineCountReporter(fileEntityCollector);

            generate(lineCountReporter, reportItemsFilter);
        }

        return lineCountReportBodies;
    }
}
