package org.linecountreporter.util;

import org.linecountreporter.report.writer.DefaultProperties;

import static org.linecountreporter.report.writer.DefaultProperties.*;

public enum AppProperty {
    TITLE(DefaultProperties.TITLE, String.class, TITLE_DEFAULT),
    FILE_TYPE(DefaultProperties.FILE_TYPE, String.class, FILE_TYPE_DEFAULT),
    LINE_COUNT_LIMIT(DefaultProperties.LINE_COUNT_LIMIT, Integer.class, LINE_COUNT_LIMIT_DEFAULT),
    OUTPUT_FILENAME(DefaultProperties.OUTPUT_FILENAME, String.class, OUTPUT_FILENAME_DEFAULT);

    private final String propertyName;
    private final Class<?> propertyType;
    private final String defaultValue;

    AppProperty(String propertyName, Class<?> propertyType, String defaultValue) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.defaultValue = defaultValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
