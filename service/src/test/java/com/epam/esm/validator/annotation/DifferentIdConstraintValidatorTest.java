package com.epam.esm.validator.annotation;

import com.epam.esm.validator.annotation.impl.DifferentIdConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentIdConstraintValidatorTest {
    DifferentIdConstraintValidator differentIdConstraintValidator = new DifferentIdConstraintValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(List<Long> value, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = differentIdConstraintValidator.isValid(value, context);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        List<Long> ids1 = new ArrayList<>();
        ids1.add(10L);
        ids1.add(570L);
        ids1.add(8L);
        List<Long> ids2 = new ArrayList<>();
        ids2.add(147L);
        ids2.add(147L);
        ids2.add(8L);
        List<Long> ids3 = new ArrayList<>();
        ids3.add(5L);
        ids3.add(5L);
        ids3.add(5L);
        return new Object[][]{
                {ids1, true},
                {ids2,false},
                {ids3,false},
                {null, true},
                {new ArrayList<>(), true}
        };
    }
}