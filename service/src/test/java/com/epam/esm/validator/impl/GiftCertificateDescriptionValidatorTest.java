package com.epam.esm.validator.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateDescriptionValidatorTest {
    GiftCertificateDescriptionValidator descriptionValidator = new GiftCertificateDescriptionValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(String description, boolean expected) {
        boolean actual = descriptionValidator.isValid(description);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"", false},
                {"r", true},
                {"4 gifts", true},
                {"gift", true},
                {"Winter is a nice season. Some people like summer or autumn, but others like winter. " +
                        "December, January and February are winter months. But in some places winter begins " +
                        "in November and ends in March or April. The nights in winter are very long, and it is" +
                        " dark when we get up in the morning. The days are short. The ground, the fields and the" +
                        " trees are white with snow. Sometimes it is very cold, and people put on their warm " +
                        "clothes when they go out. Boys and girls like to skate and ski. You can see a lot of " +
                        "children running and jumping in the snow or playing snowballs. Little children like to " +
                        "make snowmen. Do you like winter? Why?",false}
        };
    }
}