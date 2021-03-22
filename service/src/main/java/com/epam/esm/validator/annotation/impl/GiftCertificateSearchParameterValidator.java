package com.epam.esm.validator.annotation.impl;

import com.epam.esm.querybuilder.GiftCertificateSearchParameterName;
import com.epam.esm.validator.CommonValidator;
import com.epam.esm.validator.annotation.SearchParametersValid;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Map;

public class GiftCertificateSearchParameterValidator implements ConstraintValidator<SearchParametersValid, Map<String, String>> {

    @Override
    public boolean isValid(Map<String, String> parameters, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (!ObjectUtils.isEmpty(parameters)) {
            isValid = parameters.entrySet().stream()
                    .allMatch(p -> IsParameterNameValid(p.getKey()) && IsParametersValueValid(p.getKey(), p.getValue())
                            || !IsParameterNameValid(p.getKey()));
        }
        return isValid;
    }

    private static boolean IsParameterNameValid(String parameterName) {
        return Arrays.stream(GiftCertificateSearchParameterName.values())
                .anyMatch(p -> p.getSearchName().equals(parameterName));
    }

    private static boolean IsParametersValueValid(String parameterName, String parameterValue) {
        boolean isValid = false;
        if (GiftCertificateSearchParameterName.getSearchParameterName(parameterName).isPresent()) {
            GiftCertificateSearchParameterName searchParameterName = GiftCertificateSearchParameterName
                    .getSearchParameterName(parameterName).get();
            CommonValidator validator = searchParameterName.getValidator();
            isValid = validator.isValid(parameterValue);
        }
        return isValid;
    }
}