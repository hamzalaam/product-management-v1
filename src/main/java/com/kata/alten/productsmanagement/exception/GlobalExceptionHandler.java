package com.kata.alten.productsmanagement.exception;

import com.kata.alten.productsmanagement.gen.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.OffsetDateTime;

/**
 * handle global expections
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle Product Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getExceptionEnum().getHttpStatus().value());
        errorResponse.setMessage(ex.getExceptionEnum().getMessage());
        errorResponse.setErrorCode(ex.getExceptionEnum().getCode());
        errorResponse.setTimestamp(OffsetDateTime.now());
        return new ResponseEntity<>(errorResponse, ex.getExceptionEnum().getHttpStatus());
    }
}
