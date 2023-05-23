package org.linecountreporter.report.writer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.reporter.LineCountReporter;
import org.linecountreporter.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineCountReportBodyWriterTest {
    private final Path LINE_COUNT_REPORT_WRITER_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer");

    @AfterEach
    public void cleanup() {
        File file = new File(LINE_COUNT_REPORT_WRITER_PATH + "/outputFilename");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void generate_report_text_document_according_to_properties_file() throws IOException {
        FileEntityCollector fileEntityCollector = new FileEntityCollector(LINE_COUNT_REPORT_WRITER_PATH);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileEntityCollector);

        ReportArguments reportArguments = new ReportArguments();
        reportArguments.setTitle("title");
        reportArguments.setFileType("");
        String outputFilename = "outputFilename";
        reportArguments.setOutputFilename(outputFilename);
        LineCountReportWriter lineCountReportWriter = new LineCountReportWriter(lineCounterReporter, reportArguments);
        lineCountReportWriter.writeReport(Boolean.parseBoolean(reportArguments.getRecursive()));

        File reportFile = new File(LINE_COUNT_REPORT_WRITER_PATH + "/" + outputFilename);
        assertTrue(reportFile.exists());

        List<String> fileLines = extractLinesFromOutputFile(reportFile);
        
        assertEquals(11, fileLines.size());
    }

    // TODO - Write more tests with other paths (filetype-test etc) and variations of the properties file (probably with some abstraction as opposed to the prop file itself)

    private List<String> extractLinesFromOutputFile(File file) {
        List<String> fileLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
            fileLines = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }
}