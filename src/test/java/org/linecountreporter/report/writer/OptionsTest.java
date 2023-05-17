package org.linecountreporter.report.writer;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.ReportItem;

import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsTest {

    @Test
    public void given_known_config_properties_then_include_in_filters() {
        Properties config = new Properties();
        config.setProperty("fileType", "py");
        config.setProperty("lineCountLimit", "100");
        config.setProperty("lineCountMeasure", "100");
        Options options = new Options(config);

        List<Predicate<ReportItem>> filters = options.getFilters();
        assertEquals(3, filters.size());
    }

    @Test
    public void given_unknown_config_properties_or_null_values_then_dont_include_in_filters() {
        Properties config = new Properties();
        config.setProperty("fileType", "txt");
        config.setProperty("UNKNOWN_PROPERTY", "100");
        config.setProperty("lineCountMeasure", "100");
        Options options = new Options(config);

        List<Predicate<ReportItem>> filters = options.getFilters();
        assertEquals(2, filters.size());
    }

    @Test
    public void given_line_count_limit_is_wrong_type_then_throw_exception() {
        Properties config = new Properties();
        String badValue = "Not a Number!";
        config.setProperty("lineCountLimit", badValue);
        Options options = new Options(config);

        Exception exception = assertThrows(IllegalArgumentException.class, options::getFilters);

        assertEquals("Invalid lineCountLimit value: " + badValue, exception.getMessage());
    }

    @Test
    public void given_line_count_measure_is_wrong_type_then_throw_exception() {
        Properties config = new Properties();
        String badValue = "Not a Number!";
        config.setProperty("lineCountMeasure", badValue);
        Options options = new Options(config);

        Exception exception = assertThrows(IllegalArgumentException.class, options::getFilters);

        assertEquals("Invalid lineCountMeasure value: " + badValue, exception.getMessage());
    }
}