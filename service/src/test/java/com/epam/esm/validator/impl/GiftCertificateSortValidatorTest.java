package com.epam.esm.validator.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSortValidatorTest {
    GiftCertificateSortValidator sortValidator = new GiftCertificateSortValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(String sort, boolean expected) {
        boolean actual = sortValidator.isValid(sort);
        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"", false},
                {"nameGiftCertificate,-createDate", true},
                {"-nameGiftCertificate", true},
                {"createDate", true},
                {"gift", false},
                {"nameGiftCertificate,-createDate,lastUpdate",false}
        };
    }
}