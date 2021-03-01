package com.epam.esm.validator.annotation.impl;

import com.epam.esm.validator.annotation.Different;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DifferentIdConstraintValidator implements ConstraintValidator<Different, List<Long>> {
    @Override
    public void initialize(Different constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (!ObjectUtils.isEmpty(value)) {
            Map<Long, Long> ids = value.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
            isValid = ids.entrySet().stream().allMatch(id -> id.getValue() == 1);
        }
        return isValid;
    }
}