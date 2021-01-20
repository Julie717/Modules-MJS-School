package com.epam.esm.controller.advice.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorFieldValidationInfo {
    @NotNull
    String fieldName;

    @NotNull
    String errorCode;

    @NotNull
    Object rejectedValue;

    String errorMessage;
}