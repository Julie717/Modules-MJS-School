package com.epam.esm.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySearchBuilderTest {
    PartQuerySearchBuilder partQuerySearchBuilder = new PartQuerySearchBuilder();

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySearchBuilder.build(value, parameterNameInDb);
        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"gift", "name_gift_certificate", "name_gift_certificate LIKE '%gift%'"},
                {"beautiful", "description", "description LIKE '%beautiful%'"},
                {"4 gifts", "description", "description LIKE '%4 gifts%'"}
        };
    }
}