package com.epam.esm.validator;

import com.epam.esm.model.querybuilder.GiftCertificateSearchParameterName;

import java.util.Arrays;
import java.util.Map;

public class GiftCertificateSearchParameterValidator {
    public static boolean isParametersValid(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .allMatch(p -> IsParameterNameValid(p.getKey()) && IsParametersValueValid(p.getKey(), p.getValue()));
    }

    private static boolean IsParameterNameValid(String parameterName) {
        return Arrays.stream(GiftCertificateSearchParameterName.values())
                .anyMatch(p -> p.getParameterName().equals(parameterName));
    }

    private static boolean IsParametersValueValid(String parameterName, String parameterValue) {
        GiftCertificateSearchParameterName searchParameterName = GiftCertificateSearchParameterName
                .getSearchParameterName(parameterName).get();
        CommonValidator validator = searchParameterName.getValidator();
        return validator.isValid(parameterValue);
    }
}