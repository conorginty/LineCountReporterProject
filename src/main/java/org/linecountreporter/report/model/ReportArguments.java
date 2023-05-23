package org.linecountreporter.report.model;

import lombok.Data;

import java.nio.file.Path;

@Data
public class ReportArguments {
    private Path rootPath;
    private String title;
    private String fileType;
    private String lineCountLimit;
    private String outputFilename;
    private String recursive;
}
