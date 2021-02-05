package com.epam.esm.querybuilder.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartQuerySortBuilderTest {
    PartQuerySortBuilder partQuerySortBuilder = new PartQuerySortBuilder();

    @ParameterizedTest
    @MethodSource("data")
    public void buildTest(String value, String parameterNameInDb, String expected) {
        String actual = partQuerySortBuilder.build(value, parameterNameInDb);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        return new Object[][]{
                {"nameGiftCertificate,-createDate", null, "ORDER BY gift.name ASC, gift.createDate DESC"},
                {"createDate", null, "ORDER BY gift.createDate ASC"},
                {"createDate,-nameGiftCertificate", null, "ORDER BY gift.createDate ASC, gift.name DESC"}
        };
    }
}
