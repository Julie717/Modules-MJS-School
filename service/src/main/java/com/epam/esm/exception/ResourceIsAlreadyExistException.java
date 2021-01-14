package com.epam.esm.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceIsAlreadyExistException extends RuntimeException {
    String nameResource;

    public ResourceIsAlreadyExistException(String message, String nameResource) {
        super(message);
        this.nameResource = nameResource;
    }
}