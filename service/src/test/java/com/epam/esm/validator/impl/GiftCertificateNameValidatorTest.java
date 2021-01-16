package com.epam.esm.validator.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateNameValidatorTest {
    GiftCertificateNameValidator nameValidator = new GiftCertificateNameValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(String name, boolean expected) {
        boolean actual = nameValidator.isValid(name);
        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"", false},
                {"r", false},
                {"yn", true},
                {"4 gifts", true},
                {"gift", true},
                {"Winter is a nice season. Some people like summer or autumn, but others like winter. " +
                        "December, January and February are winter months. But in some places winter begins " +
                        "in November and ends in March or April. The nights in winter are very long, and it is",false}
        };
    }
}
