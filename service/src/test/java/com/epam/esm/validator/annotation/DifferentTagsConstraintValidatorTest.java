package com.epam.esm.validator.annotation;

import com.epam.esm.model.TagDto;
import com.epam.esm.validator.annotation.impl.DifferentTagsConstraintValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentTagsConstraintValidatorTest {
    DifferentTagsConstraintValidator tagsConstraintValidator = new DifferentTagsConstraintValidator();

    @ParameterizedTest
    @MethodSource("data")
    public void isValidTest(List<TagDto> value, boolean expected) {
        ConstraintValidatorContext context = null;

        boolean actual = tagsConstraintValidator.isValid(value, context);

        assertEquals(expected, actual);
    }

    public static Object[][] data() {
        List<TagDto> value1 = new ArrayList<>();
        value1.add(new TagDto(1L,"gift"));
        value1.add(new TagDto(2L,"wonderful"));
        List<TagDto> value2 = new ArrayList<>();
        value2.add(new TagDto(1L,"gift"));
        value2.add(new TagDto(2L,"wonderful"));
        value2.add(new TagDto(1L,"gift"));
        return new Object[][]{
                {value1, true},
                {value2, false},
                {new ArrayList<>(), true},
                {null, true}
        };
    }
}