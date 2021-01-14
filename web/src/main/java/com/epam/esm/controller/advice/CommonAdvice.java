package com.epam.esm.controller.advice;

import com.epam.esm.controller.advice.error.ErrorCode;
import com.epam.esm.controller.advice.error.ErrorFieldValidationInfo;
import com.epam.esm.controller.advice.error.ErrorResponse;
import com.epam.esm.exception.IllegalParameterException;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.util.ErrorMessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;

import static com.epam.esm.controller.advice.error.ErrorCode.RESOURCE_NOT_FOUND;
import static com.epam.esm.controller.advice.error.ErrorCode.RESOURCE_ALREADY_EXIST;

@EnableWebMvc
@ControllerAdvice
public class CommonAdvice {
    private final MessageSource messageSource;

    @Autowired
    public CommonAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        String errorMessage = String.format(messageSource.getMessage(ex.getMessage(), new Object[]{},
                locale), ex.getIdResource(), ex.getNameResource());
        ErrorResponse errorResponse = new ErrorResponse(RESOURCE_NOT_FOUND, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceIsAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleResourceIsAlreadyExistException(ResourceIsAlreadyExistException ex, Locale locale) {
        String errorMessage = String.format(messageSource.getMessage(ex.getMessage(), new Object[]{},
                locale), ex.getNameResource());
        ErrorResponse errorResponse = new ErrorResponse(RESOURCE_ALREADY_EXIST, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalParameterException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(IllegalParameterException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), new Object[]{},
                locale);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST_VALUE,
                messageSource.getMessage(ErrorMessageReader.INCORRECT_VALUE, new Object[]{}, locale));
        List<ErrorFieldValidationInfo> fields = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        for (FieldError field : result.getFieldErrors()) {
            ErrorFieldValidationInfo errorInfo = new ErrorFieldValidationInfo();
            errorInfo.setFieldName(field.getField());
            errorInfo.setErrorCode(field.getCode());
            errorInfo.setRejectedValue(field.getRejectedValue());
            if (field.getDefaultMessage() != null) {
                errorInfo.setErrorMessage(messageSource.getMessage(field.getDefaultMessage(), new Object[]{},
                        locale));
            }
            fields.add(errorInfo);
        }
        response.setErrorFieldsValidationInfo(fields);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ErrorMessageReader.INTERNAL_SERVER_ERROR, new Object[]{},
                locale);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}