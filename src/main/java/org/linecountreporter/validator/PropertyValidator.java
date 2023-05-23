package org.linecountreporter.validator;


import org.linecountreporter.util.AppProperty;

import java.util.Optional;

public class PropertyValidator {
    public static Optional<String> validateValue(String propertyName, String propertyValue) {

        Optional<AppProperty> configProperty = findConfigProperty(propertyName);

        return configProperty.flatMap(appProperty -> validatePropertyValue(appProperty, propertyValue));
    }

    private static Optional<AppProperty> findConfigProperty(String propertyName) {
        for (AppProperty configProperty : AppProperty.values()) {
            if (configProperty.getPropertyName().equals(propertyName)) {
                return Optional.of(configProperty);
            }
        }
        return Optional.empty();
    }

    private static Optional<String> validatePropertyValue(AppProperty configProperty, String propertyValue) {
        Class<?> propertyType = configProperty.getPropertyType();

        try {
            if (propertyType == String.class) {
                return Optional.of(propertyValue);
            } else if (propertyType == Boolean.class) {
                Boolean.parseBoolean(propertyValue);
                return Optional.of(propertyValue);
            } else if (propertyType == Integer.class) {
                Integer.parseInt(propertyValue);
                return Optional.of(propertyValue);
            } else {
                throw new IllegalArgumentException("Property type: " + propertyType + " not recognised");
            }
        } catch (NumberFormatException e) {
            // Log warning that the property value is not a valid number
        } catch (IllegalArgumentException e) {
            // Handle other parsing exceptions or custom validation logic
        }

        return Optional.of(configProperty.getDefaultValue());
    }
}
