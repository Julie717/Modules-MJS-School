package com.epam.esm.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceAlreadyExistsException extends RuntimeException {
    String nameResource;

    public ResourceAlreadyExistsException(String message, String nameResource) {
        super(message);
        this.nameResource = nameResource;
    }
}