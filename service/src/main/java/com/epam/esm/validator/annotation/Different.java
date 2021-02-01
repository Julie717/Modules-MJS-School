package com.epam.esm.validator.annotation;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.annotation.impl.TagsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TagsConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Different {
    String message() default ErrorMessageReader.DUPLICATE_VALUE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
