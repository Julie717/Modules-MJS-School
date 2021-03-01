package com.epam.esm.validator.annotation;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.annotation.impl.GiftCertificateSearchParameterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = GiftCertificateSearchParameterValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchParametersValid {
    String message() default ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}