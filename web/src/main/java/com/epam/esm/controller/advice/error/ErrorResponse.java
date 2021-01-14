package com.epam.esm.controller.advice.error;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ErrorResponse {
    int errorCode;
    String errorMessage;
    List<ErrorFieldValidationInfo> errorFieldsValidationInfo;

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}