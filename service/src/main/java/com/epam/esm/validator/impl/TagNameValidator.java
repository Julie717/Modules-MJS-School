package com.epam.esm.validator.impl;

import com.epam.esm.validator.CommonValidator;

import java.util.regex.Pattern;

public class TagNameValidator implements CommonValidator {
    private static final String NAME_PATTERN = "[\\w][\\w\\s]{0,44}(,[\\w][\\w\\s]{0,44})*";

    @Override
    public boolean isValid(String value) {
        return Pattern.matches(NAME_PATTERN, value);
    }
}