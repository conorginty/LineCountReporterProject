package org.linecountreporter;

import org.linecountreporter.cmd.ParametersParser;
import org.linecountreporter.file.collector.FileEntityCollector;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.reporter.LineCountReporter;
import org.linecountreporter.report.writer.LineCountReportWriter;

import java.io.IOException;

public class ReportWriterProgram {
    public void createAndWriteReport(String[] args) throws IOException {
        ReportArguments reportArguments;
        ParametersParser parametersParser = new ParametersParser();
        parametersParser.loadOptions();
        reportArguments = parametersParser.parseArguments(args);

        printRequiredArguments(reportArguments);
        printOptionalArguments(reportArguments);

        createAndWriteReport(reportArguments);
    }

    private void createAndWriteReport(ReportArguments reportArguments) throws IOException {
        FileEntityCollector fileEntityCollector = new FileEntityCollector(reportArguments.getRootPath());
        LineCountReporter lineCountReporter = new LineCountReporter(fileEntityCollector);
        LineCountReportWriter reportWriter = new LineCountReportWriter(lineCountReporter, reportArguments);

        boolean recursive = Boolean.parseBoolean(reportArguments.getRecursive());
        reportWriter.writeReport(recursive);
    }

    private void printRequiredArguments(ReportArguments reportArguments) {
        System.out.println("=========================");
        System.out.println("Passed Required Arguments");
        System.out.println("=========================");
        System.out.println("Root Directory Path: " + reportArguments.getRootPath());
    }

    private void printOptionalArguments(ReportArguments reportArguments) {
        System.out.println("=====================================================================");
        System.out.println("Passed Optional Arguments (if not passed then Default value is shown)");
        System.out.println("=====================================================================");
        System.out.println("Report Title: " + reportArguments.getTitle());
        System.out.println("File Type: " + reportArguments.getFileType());
        System.out.println("Line Count Limit: " + reportArguments.getLineCountLimit());
        System.out.println("Output Filename: " + reportArguments.getOutputFilename());
        System.out.println("Recursive Mode: " + reportArguments.getRecursive());
    }
}
