package org.linecountreporter.report.writer;

import org.linecountreporter.util.PropertyValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationReader {
    private static final String CONFIG_FILE_PATH = "config.properties";

    public static Properties readConfiguration() {
        Properties config = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }

                int equalsIndex = line.indexOf('=');
                if (equalsIndex != -1) {
                    String propertyName = line.substring(0, equalsIndex).trim();
                    String propertyValue = line.substring(equalsIndex + 1).trim();

                    Optional<String> valueOrDefault = PropertyValidator.validateValue(propertyName, propertyValue);

                    valueOrDefault.ifPresent(value -> config.setProperty(propertyName, value));
                }
            }
        } catch (IOException e) {
            // Log warning that the config file couldn't be read or some other issue
        }
        return config;
    }
}
