package org.linecountreporter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ReportWriterProgram reportWriterProgram = new ReportWriterProgram();
        reportWriterProgram.createAndWriteReport(args);
    }
}
