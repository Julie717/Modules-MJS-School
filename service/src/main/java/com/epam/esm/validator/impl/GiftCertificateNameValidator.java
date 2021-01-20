package com.epam.esm.validator.impl;

import com.epam.esm.validator.CommonValidator;

import java.util.regex.Pattern;

public class GiftCertificateNameValidator implements CommonValidator {
    private static final String NAME_PATTERN = "[\\pL\\d\\p{Punct}][\\pL\\d\\p{Punct}\\s][\\pL\\d\\p{Punct}\\s]{0,43}";

    @Override
    public boolean isValid(String value) {
        return Pattern.matches(NAME_PATTERN, value);
    }
}