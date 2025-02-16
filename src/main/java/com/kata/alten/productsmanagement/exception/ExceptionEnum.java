package com.kata.alten.productsmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * enum exception
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum {

    /**
     * enums
     */
    PRODUCT_NOT_FOUND("1000", "PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND),
    PRODUCT_ERROR_CREATION_UPDATE("1000", "PRODUCT_ERROR_CREATION_UPDATE", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("1001", "USER_NOT_FOUND", HttpStatus.NOT_FOUND),
    USER_EMAIL_EXIST("1001", "USER_EMAIL_ALREADY_EXIST", HttpStatus.NOT_FOUND),
    USER_USERNAME_EXIST("1001", "USER_USERNAME_ALREADY_EXIST", HttpStatus.NOT_FOUND),
    WEAK_PASSWORD("1001", "WEAK_PASSWORD", HttpStatus.BAD_REQUEST);

    /**
     * code message status
     */
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
