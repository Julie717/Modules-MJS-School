package com.epam.esm.validator.annotation;

import com.epam.esm.validator.annotation.impl.GiftCertificateSearchParameterValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchParameterValidatorTest {
    private final GiftCertificateSearchParameterValidator giftCertificateValidator = new GiftCertificateSearchParameterValidator();

    public static Stream<Arguments> data() {
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("nameGiftCertificate", "gift");
        parameters1.put("description", "beautiful");
        parameters1.put("sort", "nameGiftCertificate,-createDate");
        parameters1.put("page", "18");
        parameters1.put("perPage", "10");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("newDescription", "beautiful");
        parameters2.put("sort", "nameGiftCertificate,-createDate");
        Map<String, String> parameters3 = new HashMap<>();
        parameters3.put("description", "beautiful");
        parameters3.put("nameGiftCertificate", "");
        Map<String, String> parameters4 = new HashMap<>();
        Map<String, String> parameters5 = new HashMap<>();
        parameters5.put("nameTag", "beautiful,gift");
        parameters5.put("nameGiftCertificate", "a");
        Map<String, String> parameters6 = new HashMap<>();
        parameters6.put("perPage", "10");
        return Stream.of(
                Arguments.of(parameters1, true),
                Arguments.of(parameters2, true),
                Arguments.of(parameters3, false),
                Arguments.of(parameters4, false),
                Arguments.of(parameters5, true),
                Arguments.of(parameters6, true),
                Arguments.of(new HashMap<>(), false)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void isParametersValidTest(Map<String, String> parameters, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = giftCertificateValidator.isValid(parameters, context);

        assertEquals(expected, actual);
    }
}