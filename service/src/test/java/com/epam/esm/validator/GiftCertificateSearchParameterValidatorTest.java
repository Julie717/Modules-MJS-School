package com.epam.esm.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchParameterValidatorTest {

    @ParameterizedTest
    @MethodSource("data")
    public void isParametersValidTest(Map<String, String> parameters, boolean expected) {
        boolean actual = GiftCertificateSearchParameterValidator.isParametersValid(parameters);
        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("nameGiftCertificate", "gift");
        parameters1.put("description", "beautiful");
        parameters1.put("sort", "nameGiftCertificate,-createDate");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("newDescription", "beautiful");
        parameters2.put("sort", "nameGiftCertificate,-createDate");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("description", "beautiful");
        parameters3.put("nameGiftCertificate", "");
        Map<String, String> parameters4 = new HashMap<>();
        return new Object[][]{
                {parameters1, true},
                {parameters2, false},
                {parameters3, false},
                {parameters4, false}
        };
    }
}