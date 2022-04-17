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
    @Test
    void should_generate_generic_report_text_document_when_no_options_set() {
        String path = Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer";
        FileCollector fileCollector = new FileCollector(path);
        LineCountReporter lineCounterReporter = new LineCountReporter(fileCollector);
        Options options = new Options.OptionsBuilder()
            .build();

        LineCountReportWriter lineCountReportWriter =
            new LineCountReportWriter(options, lineCounterReporter);
        lineCountReportWriter.writeReport();

        File reportFile = new File(path);
        assertTrue(reportFile.exists());

        List<String> fileLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(path + "/output.txt"))) {
            fileLines = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertTrue(fileLines.size() >= 9);
    }

    @Test
    void should_generate_report_text_document_with_just_files_of_filetype_when_option_set() {
        String path = Utils.RESOURCES_DIRECTORY_PATH + "/line-count-report-writer/filetype-test";
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

        List<String> fileLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(path + "/output.txt"))) {
            fileLines = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = fileLines.stream()
            .filter(str ->str.indexOf(javaFileType)!=-1)
            .collect(Collectors.toList());

        assertEquals(2, result.size());
    }
}