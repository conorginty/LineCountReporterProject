package org.linecountreporter.report.model;

import java.util.Objects;

public class ReportItem {
    private final String filename;
    private final long lineCount;

    public ReportItem(String filename, long lineCount) {
        this.filename = filename;
        this.lineCount = lineCount;
    }

    public String getFilename() {
        return filename;
    }

    public long getLineCount() {
        return lineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportItem item = (ReportItem) o;
        return lineCount == item.lineCount && Objects.equals(filename, item.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, lineCount);
    }

    @Override
    public String toString() {
        return filename + " => " + lineCount;
    }
}
