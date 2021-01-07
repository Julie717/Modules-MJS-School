package com.epam.esm.validator;

import com.epam.esm.model.Tag;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Service
public class TagValidator implements Validator {
    private static final String NAME_PATTERN = "[\\pL\\d\\p{Punct}]{2}[\\pL\\d\\p{Punct}\\s]{0,43}";

    @Override
    public boolean supports(Class<?> clazz) {
        return Tag.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tag tag = (Tag) target;
        if (!Pattern.matches(NAME_PATTERN, tag.getName()))
            errors.rejectValue("name", "Not empty, with length from 2 to 45");
    }
}