package com.epam.esm.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IllegalParameterException extends RuntimeException {
    public IllegalParameterException(String message) {
        super(message);
    }
}