package com.epam.esm.validator.impl;

import com.epam.esm.model.querybuilder.GiftCertificateSortParameterName;
import com.epam.esm.validator.CommonValidator;

import java.util.Arrays;

public class GiftCertificateSortValidator implements CommonValidator {
    private static final String COMMA = ",";
    private static final char SIGN_MINUS = '-';

    @Override
    public boolean isValid(String value) {
        String[] sortParameters = value.split(COMMA);
        sortParameters = Arrays.stream(sortParameters).map(p -> findSortParameterName(p)).toArray(String[]::new);
        return Arrays.stream(sortParameters).allMatch(p -> isOneParameterValid(p));
    }

    private static String findSortParameterName(String value) {
        value = value.trim();
        if (value.charAt(0) == SIGN_MINUS) {
            value = value.substring(1);
        }
        return value;
    }

    private boolean isOneParameterValid(String parameter) {
        return Arrays.stream(GiftCertificateSortParameterName.values())
                .anyMatch(p -> p.getParameterName().equalsIgnoreCase(parameter));
    }
}