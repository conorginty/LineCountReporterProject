package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileCollector;
import org.linecountreporter.report.reporter.LineCountReporter;
import org.linecountreporter.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineCountReportWriterTest {
    @Test
    void should_generate_report_text_document() {
        String path = Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer";
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);
        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter("TEST TITLE", lineCounterReporter);
        lineCountReportWriter.writeReport();

        File reportFile = new File(path);
        assertTrue(reportFile.exists());

        List<String> fileLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(path + "/output.txt"))) {
            fileLines = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertEquals(9, fileLines.size());
    }
}