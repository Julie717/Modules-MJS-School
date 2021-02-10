package com.epam.esm.validator.annotation;

import com.epam.esm.validator.annotation.impl.PaginationConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationConstraintValidatorTest {
    PaginationConstraintValidator paginationValidator = new PaginationConstraintValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(Map<String, String> parameters, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = paginationValidator.isValid(parameters, context);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("limit", "5");
        parameters1.put("offset", "4");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("limit", "3");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("limit", "14");
        parameters3.put("offset", "10");
        Map<String, String> parameters4 = new HashMap<>();
        Map<String, String> parameters5 = new HashMap<>();
        parameters5.put("limit", "-10");
        Map<String, String> parameters6 = new HashMap<>();
        parameters6.put("limit", "14");
        parameters6.put("offset", "10d");
        Map<String, String> parameters7 = new HashMap<>();
        parameters7.put("limit", "max");
        return new Object[][]{
                {parameters1, true},
                {parameters2, true},
                {parameters3, true},
                {parameters4, false},
                {parameters5, false},
                {parameters6, false},
                {parameters7, false}
        };
    }
}