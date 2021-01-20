package com.epam.esm.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceNotFoundException extends RuntimeException {
    int idResource;
    String nameResource;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, int idResource) {
        super(message);
        this.idResource = idResource;
    }

    public ResourceNotFoundException(String message, int idResource, String nameResource) {
        super(message);
        this.idResource = idResource;
        this.nameResource = nameResource;
    }
}