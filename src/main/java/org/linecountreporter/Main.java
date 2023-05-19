package org.linecountreporter;

public class Main {
    public static void main(String[] args) {
        ReportWriterProgram reportWriterProgram = new ReportWriterProgram();
        reportWriterProgram.createAndWriteReport(args);
    }
}
