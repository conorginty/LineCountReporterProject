package org.linecountreporter.validator;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyValidatorTest {

    @Test
    public void given_valid_property_name_and_value_then_same_value_returned() {
        Optional<String> validatedValue = PropertyValidator.validateValue("lineCountLimit", "2");
        assertTrue(validatedValue.isPresent());
        assertEquals("2", validatedValue.get());
    }

    @Test
    public void given_invalid_property_name_and_value_then_value_is_empty() {
        Optional<String> actual = PropertyValidator.validateValue("UNKNOWN PROPERTY", "abc");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void given_valid_property_name_but_invalid_value_then_default_value_returned() {
        Optional<String> validatedValue = PropertyValidator.validateValue("lineCountLimit", "Not a Number");
        assertTrue(validatedValue.isPresent());
        assertEquals("0", validatedValue.get());
    }
}