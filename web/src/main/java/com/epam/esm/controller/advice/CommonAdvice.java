package com.epam.esm.controller.advice;

import com.epam.esm.controller.Response;
import com.epam.esm.exception.IncorrectValueException;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Response response = new Response(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceIsAlreadyExistException.class)
    public ResponseEntity<Response> handleResourceIsAlreadyExistException(ResourceIsAlreadyExistException ex) {
        Response response = new Response(ex.getMessage(),HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectValueException.class)
    public ResponseEntity<Response> handleIncorrectValueException(IncorrectValueException ex) {
        Response response = new Response(ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}