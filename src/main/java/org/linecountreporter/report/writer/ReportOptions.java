package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.model.ReportItem;

import java.util.function.Predicate;

import static org.linecountreporter.report.writer.DefaultProperties.LINE_COUNT_LIMIT;

public class ReportOptions {
    private static final String INVALID_VALUE_MSG = "Invalid %s value: ";
    private final ReportArguments reportArgs;

    public ReportOptions(ReportArguments reportArgs) {
        this.reportArgs = reportArgs;
    }

    public Predicate<ReportItem> fileTypeFilter() {
        String fileType = reportArgs.getFileType();
        if (fileType != null) {
            return item -> item.getFilename().endsWith(fileType);
        }
        return item -> true;
    }

    public Predicate<ReportItem> limitLineCountFilter() {
        String lineCountLimitStr = reportArgs.getLineCountLimit();
        if (lineCountLimitStr != null) {
            try {
                long lineCountLimit = Long.parseLong(lineCountLimitStr);
                return item -> item.getLineCount() <= lineCountLimit;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format(INVALID_VALUE_MSG, LINE_COUNT_LIMIT) + lineCountLimitStr);
            }
        }
        return item -> true;
    }
}
