package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.LineCountReportBody;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.reporter.LineCountReporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class LineCountReportWriter {

    private final Path directoryPath;
    private final LineCountReporter lineCountReporter;
    private final ReportItemsFilter reportItemsFilter;
    private final String outputFilename;
    private final String title;

    public LineCountReportWriter(LineCountReporter lineCountReporter, ReportArguments reportArguments) {
        this.lineCountReporter = lineCountReporter;
        this.directoryPath = lineCountReporter.getPath();
        ReportOptions reportOptions = new ReportOptions(reportArguments);
        this.reportItemsFilter = new ReportItemsFilter(reportOptions);
        this.outputFilename = reportArguments.getOutputFilename();
        this.title = reportArguments.getTitle();
    }

    public void writeReport(boolean recursive) throws IOException {
        String outFile = String.valueOf(this.directoryPath.resolve(this.outputFilename));

        writeHeader(outFile);
        
        ReportGenerator reportGenerator;
        if (recursive) {
            reportGenerator = new RecursiveReportGenerator();
            List<LineCountReportBody> lineCountReportBodies = (List<LineCountReportBody>) reportGenerator.generate(lineCountReporter, reportItemsFilter);

            try (FileWriter writer = new FileWriter(outFile, StandardCharsets.UTF_8, true);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (LineCountReportBody body: lineCountReportBodies) {
                    bufferedWriter.append(body.toString());
                }
            } catch (IOException e) {
                throw new IOException("There was an issue writing to: " + outFile);
            }
        } else {
            reportGenerator = new SingleReportGenerator();
            LineCountReportBody lineCountReportBody = (LineCountReportBody) reportGenerator.generate(lineCountReporter, reportItemsFilter);

            try (FileWriter writer = new FileWriter(outFile, StandardCharsets.UTF_8, true);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                bufferedWriter.append(lineCountReportBody.toString());
            } catch (IOException e) {
                throw new IOException("There was an issue writing to: " + outFile);
            }
        }
    }

    private void writeHeader(String outFile) {
        try (FileWriter writer = new FileWriter(outFile, StandardCharsets.UTF_8);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(createHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createHeader() {
        StringBuilder sb = new StringBuilder();
        generateTitleWrapper(sb);
        sb.append(title);
        sb.append("\n");
        generateTitleWrapper(sb);
        return sb.toString();
    }

    private void generateTitleWrapper(StringBuilder sb) {
        sb.append("*".repeat(this.title.length()));
        sb.append("\n");
    }
}
