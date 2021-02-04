package com.epam.esm.validator.annotation.impl;

import com.epam.esm.model.TagDto;
import com.epam.esm.validator.annotation.Different;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TagsConstraintValidator implements ConstraintValidator<Different, List<TagDto>> {
    @Override
    public void initialize(Different constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<TagDto> value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value != null && !value.isEmpty()) {
            Map<Long, Long> ids = value.stream().filter(v -> v.getId() != null)
                    .collect(Collectors.groupingBy(TagDto::getId, Collectors.counting()));
            Map<String, Long> names = value.stream()
                    .filter(v -> v.getName() == null || v.getName().isEmpty())
                    .collect(Collectors.groupingBy(TagDto::getName, Collectors.counting()));
            isValid = ids.entrySet().stream().allMatch(id -> id.getValue() == 1) &&
                    names.entrySet().stream().allMatch(name -> name.getValue() == 1);
        }
        return isValid;
    }
}