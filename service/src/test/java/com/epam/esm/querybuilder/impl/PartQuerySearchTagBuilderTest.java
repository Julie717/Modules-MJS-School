package com.epam.esm.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySearchTagBuilderTest {
    PartQuerySearchTagBuilder partQuerySearchTagBuilder = new PartQuerySearchTagBuilder();

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySearchTagBuilder.build(value, parameterNameInDb);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"gift,beautiful,a", "tag.name", "tag.name LIKE CONCAT('%', 'gift', '%') " +
                        "OR tag.name LIKE CONCAT('%', 'beautiful', '%') OR tag.name LIKE CONCAT('%', 'a', '%')"},
                {"first", "tag.name", "tag.name LIKE CONCAT('%', 'first', '%')"},
                {"4 gifts,s", "tag.name", "tag.name LIKE CONCAT('%', '4 gifts', '%') " +
                        "OR tag.name LIKE CONCAT('%', 's', '%')"}
        };
    }
}