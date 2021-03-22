package com.epam.esm.validator.annotation;

import com.epam.esm.validator.annotation.impl.PaginationConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationConstraintValidatorTest {
    private final PaginationConstraintValidator paginationValidator = new PaginationConstraintValidator();

    public static Stream<Arguments> data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("page", "5");
        parameters1.put("perPage", "4");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("perPage", "3");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("page", "14");
        parameters3.put("perPage", "10");
        Map<String, String> parameters4 = new HashMap<>();
        Map<String, String> parameters5 = new HashMap<>();
        parameters5.put("perPage", "-10");
        Map<String, String> parameters6 = new HashMap<>();
        parameters6.put("page", "14");
        parameters6.put("perPage", "10d");
        Map<String, String> parameters7 = new HashMap<>();
        parameters7.put("page", "max");
        Map<String, String> parameters8 = new HashMap<>();
        parameters8.put("page", "14000000");
        parameters8.put("perPage", "15");
        Map<String, String> parameters9 = new HashMap<>();
        parameters9.put("page", "100");
        parameters9.put("perPage", "2000001");
        return Stream.of(
                Arguments.of(parameters1, true),
                Arguments.of(parameters2, true),
                Arguments.of(parameters3, true),
                Arguments.of(parameters4, false),
                Arguments.of(parameters5, false),
                Arguments.of(parameters6, false),
                Arguments.of(parameters7, false),
                Arguments.of(parameters8, false),
                Arguments.of(parameters9, false)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(Map<String, String> parameters, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = paginationValidator.isValid(parameters, context);

        assertEquals(expected, actual);
    }
}