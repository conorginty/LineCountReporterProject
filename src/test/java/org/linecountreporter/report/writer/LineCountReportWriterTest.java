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

import static org.junit.jupiter.api.Assertions.*;

class LineCountReportWriterTest {
    private final String LINE_COUNT_REPORT_WRITER_PATH = Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer";

    @Test
    void should_generate_generic_report_text_document_when_no_options_set() {
        FileCollector fileCollector = new FileCollector(LINE_COUNT_REPORT_WRITER_PATH);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);
        Options options = new Options.OptionsBuilder()
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(options, lineCounterReporter);
        lineCountReportWriter.writeReport();

        File reportFile = new File(LINE_COUNT_REPORT_WRITER_PATH);
        assertTrue(reportFile.exists());

        List<String> fileLines = extractFileLines(LINE_COUNT_REPORT_WRITER_PATH);
        
        assertTrue(fileLines.size() >= 9);
    }

    private List<String> extractFileLines(String path) {
        List<String> fileLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(path + "/output.txt"))) {
            fileLines = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

    @Test
    void should_generate_report_text_document_with_just_files_of_filetype_when_option_set() {
        String path = LINE_COUNT_REPORT_WRITER_PATH + "/filetype-test";
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);

        String javaFileType = "java";
        Options options = new Options.OptionsBuilder()
            .fileType(javaFileType)
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(options, lineCounterReporter);
        lineCountReportWriter.writeReport();

        File outputFile = new File(path + "/output.txt");
        assertTrue(outputFile.exists());

        List<String> fileLines = extractFileLines(path);

        List<String> result = fileLines.stream()
            .filter(line -> line.contains(javaFileType))
            .collect(Collectors.toList());

        assertEquals(2, result.size());
    }

    @Test
    void should_generate_report_text_document_with_just_files_with_line_count_up_to_and_including_limit_when_option_set() {
        String path = LINE_COUNT_REPORT_WRITER_PATH + "/line-count-limit-test";
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);

        Options options = new Options.OptionsBuilder()
            .lineCountLimit(5)
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(options, lineCounterReporter);
        lineCountReportWriter.writeReport();

        File outputFile = new File(path + "/output.txt");
        assertTrue(outputFile.exists());

        List<String> fileLines = extractFileLines(path);

        List<String> result = fileLines.stream()
            .filter(line -> line.contains("txt"))
            .collect(Collectors.toList());

        assertEquals(2, result.size());
    }
}