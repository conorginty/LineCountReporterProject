package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.file.collector.FileCollector;
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

class LineCountReportWriterTest {
    private final Path LINE_COUNT_REPORT_WRITER_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer");

    @Test
    void should_generate_generic_report_text_document_when_no_options_set() throws Exception {
        FileCollector fileCollector = new FileCollector(LINE_COUNT_REPORT_WRITER_PATH);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);
        Options options = new Options.OptionsBuilder()
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(lineCounterReporter, options);
        lineCountReportWriter.writeReport();

        File reportFile = new File(LINE_COUNT_REPORT_WRITER_PATH + "/output.txt");
        assertTrue(reportFile.exists());

        List<String> fileLines = extractLinesFromOutputFile(reportFile);
        
        assertEquals(10, fileLines.size());
    }

    @Test
    void should_generate_report_text_document_with_just_files_of_filetype_when_option_set() {
        Path path = Path.of(LINE_COUNT_REPORT_WRITER_PATH + "/filetype-test");
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);

        String javaFileType = "java";
        Options options = new Options.OptionsBuilder()
            .fileType(javaFileType)
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(lineCounterReporter, options);
        lineCountReportWriter.writeReport();

        File reportFile = new File(path + "/output.txt");
        assertTrue(reportFile.exists());

        List<String> fileLines = extractLinesFromOutputFile(reportFile);

        List<String> result = fileLines.stream()
            .filter(line -> line.contains(javaFileType))
            .collect(Collectors.toList());

        assertEquals(2, result.size());
    }

    @Test
    void should_generate_report_text_document_with_just_files_with_line_count_up_to_and_including_limit_when_option_set() {
        Path path = Path.of(LINE_COUNT_REPORT_WRITER_PATH + "/line-count-limit-test");
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);

        Options options = new Options.OptionsBuilder()
            .lineCountLimit(5)
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(lineCounterReporter, options);
        lineCountReportWriter.writeReport();

        File reportFile = new File(path + "/output.txt");
        assertTrue(reportFile.exists());

        List<String> fileLines = extractLinesFromOutputFile(reportFile);

        List<String> result = fileLines.stream()
            .filter(line -> line.contains("txt"))
            .collect(Collectors.toList());

        assertEquals(2, result.size());
    }

    @Test
    void should_generate_report_text_document_with_line_count_measure_output_when_option_set() {
        Path path = Path.of(LINE_COUNT_REPORT_WRITER_PATH + "/line-count-measure-test");
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);

        Options options = new Options.OptionsBuilder()
            .lineCountMeasure(5)
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(lineCounterReporter, options);
        lineCountReportWriter.writeReport();

        File reportFile = new File(path + "/output.txt");
        assertTrue(reportFile.exists());

        List<String> fileLines = extractLinesFromOutputFile(reportFile);

        List<String> result = fileLines.stream()
            .filter(line -> line.matches(".* \\((\\d*\\.?\\d+|\\d+)%\\)"))
            .collect(Collectors.toList());

        assertEquals(4, result.size());
    }

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