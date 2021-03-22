package com.epam.esm.validator.annotation.impl;

import com.epam.esm.validator.annotation.IncludePagination;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class PaginationConstraintValidator implements ConstraintValidator<IncludePagination, Map<String, String>> {
    private static final String PAGE = "page";
    private static final String PER_PAGE = "perPage";
    private static final String NUMBER = "\\d+";
    private static final String NUMBER_EXCLUDE_ZERO = "[1-9]\\d*";
    private static final int MAX_VALUE = 2000000;

    @Override
    public void initialize(IncludePagination constraintAnnotation) {
    }

    @Override
    public boolean isValid(Map<String, String> value, ConstraintValidatorContext context) {
        String page = value.get(PAGE);
        String perPage = value.get(PER_PAGE);
        boolean isValid = !ObjectUtils.isEmpty(perPage) && Pattern.matches(NUMBER_EXCLUDE_ZERO, perPage)
                && isIntegerNumber(perPage);
        if (!ObjectUtils.isEmpty(page)) {
            isValid = isValid && Pattern.matches(NUMBER, page) && isIntegerNumber(page);
        }
        return isValid;
    }

    private boolean isIntegerNumber(String number) {
        BigInteger pageNumber = new BigInteger(number);
        return pageNumber.compareTo(BigInteger.valueOf(MAX_VALUE)) < 0;
    }
}