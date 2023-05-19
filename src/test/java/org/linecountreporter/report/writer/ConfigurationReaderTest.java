package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.linecountreporter.report.writer.DefaultProperties.*;

public class ConfigurationReaderTest {
    @Test
    public void hardcoded_properties_file_is_read_from() {
        Properties properties = ConfigurationReader.readConfiguration();
        assertEquals("LINE COUNT REPORT", properties.getProperty(TITLE));
        assertEquals("txt", properties.getProperty(FILE_TYPE));
        assertEquals("1000", properties.getProperty(LINE_COUNT_LIMIT));
    }
}