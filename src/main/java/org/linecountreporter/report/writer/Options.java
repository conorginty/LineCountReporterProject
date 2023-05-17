package org.linecountreporter.report.writer;

import org.linecountreporter.report.model.ReportItem;

import java.util.*;
import java.util.function.Predicate;

public class Options {
    private static final String INVALID_VALUE_MSG = "Invalid %s value: ";
    private static final String LINE_COUNT_MEASURE = "lineCountMeasure";
    private static final String LINE_COUNT_LIMIT = "lineCountLimit";
    private final Properties config;

    public Options(Properties config) {
        this.config = config;
    }

    public List<Predicate<ReportItem>> getFilters() {
        List<Predicate<ReportItem>> filters = new ArrayList<>();

        Optional<Predicate<ReportItem>> fileTypeFilter = getFileTypeFilter();
        fileTypeFilter.ifPresent(filters::add);

        Optional<Predicate<ReportItem>> lineCountLimitFilter = getLineCountLimitFilter();
        lineCountLimitFilter.ifPresent(filters::add);

        Optional<Predicate<ReportItem>> lineCountMeasureFilter = getLineCountMeasureFilter();
        lineCountMeasureFilter.ifPresent(filters::add);

        return filters;
    }

    private Optional<Predicate<ReportItem>> getFileTypeFilter() {
        String fileType = config.getProperty("fileType");
        if (fileType != null) {
            Predicate<ReportItem> filter = item -> item.getFilename().endsWith(fileType);
            return Optional.of(filter);
        }
        return Optional.empty();
    }

    private Optional<Predicate<ReportItem>> getLineCountLimitFilter() {
        String lineCountLimitStr = config.getProperty(LINE_COUNT_LIMIT);
        if (lineCountLimitStr != null) {
            try {
                long lineCountLimit = Long.parseLong(lineCountLimitStr);
                Predicate<ReportItem> filter = item -> item.getLineCount() <= lineCountLimit;
                return Optional.of(filter);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format(INVALID_VALUE_MSG, LINE_COUNT_LIMIT) + lineCountLimitStr);
            }
        }
        return Optional.empty();
    }

    private Optional<Predicate<ReportItem>> getLineCountMeasureFilter() {
        String lineCountMeasureStr = config.getProperty(LINE_COUNT_MEASURE);
        if (lineCountMeasureStr != null) {
            try {
                double lineCountMeasure = Double.parseDouble(lineCountMeasureStr);
                Predicate<ReportItem> filter = item -> {
                    double result = calculateMeasureResult(lineCountMeasure, item.getLineCount());
                    return result >= lineCountMeasure;
                };
                return Optional.of(filter);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format(INVALID_VALUE_MSG, LINE_COUNT_MEASURE) + lineCountMeasureStr);
            }
        }
        return Optional.empty();
    }

    private double calculateMeasureResult(double lineCountMeasure, long lineCount) {
        return lineCount / lineCountMeasure * 100;
    }
}
