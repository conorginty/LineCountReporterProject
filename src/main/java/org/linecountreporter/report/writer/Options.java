package org.linecountreporter.report.writer;

public class Options {
    private final String title;
    private final String fileType;
    private final long lineCountLimit;
    private final long lineCountMeasure;

    public Options(OptionsBuilder builder) {
        this.title = builder.title;
        this.fileType = builder.fileType;
        this.lineCountLimit = builder.lineCountLimit;
        this.lineCountMeasure = builder.lineCountMeasure;
    }

    public String getTitleOrDefault() {
        if (this.title == null) {
            return "LINE COUNT REPORT";
        }
        return this.title;
    }

    public String getFileTypeOrDefault() {
        if (this.fileType == null) {
            return "";
        }
        return this.fileType;
    }

    public long getLineCountLimit() {
        return lineCountLimit;
    }

    public long getLineCountMeasure() {
        return lineCountMeasure;
    }

    public static class OptionsBuilder {
        private String title;
        private String fileType;
        private long lineCountLimit;
        private long lineCountMeasure;

        public OptionsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public OptionsBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public OptionsBuilder lineCountLimit(long lineCountLimit) {
            this.lineCountLimit = lineCountLimit;
            return this;
        }

        public OptionsBuilder lineCountMeasure(long lineCountMeasure) {
            this.lineCountMeasure = lineCountMeasure;
            return this;
        }

        public Options build() {
            Options options = new Options(this);
            return options;
        }

    }
}
