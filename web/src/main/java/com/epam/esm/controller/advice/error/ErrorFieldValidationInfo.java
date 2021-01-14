package com.epam.esm.controller.advice.error;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ErrorFieldValidationInfo {
    @NonNull
    String fieldName;

    @NonNull
    String errorCode;

    @NonNull
    Object rejectedValue;

    String errorMessage;
}