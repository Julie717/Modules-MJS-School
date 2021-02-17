package com.epam.esm.validator.annotation;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.annotation.impl.DifferentIdConstraintValidator;
import com.epam.esm.validator.annotation.impl.DifferentTagsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {DifferentTagsConstraintValidator.class, DifferentIdConstraintValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Different {
    String message() default ErrorMessageReader.DUPLICATE_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}