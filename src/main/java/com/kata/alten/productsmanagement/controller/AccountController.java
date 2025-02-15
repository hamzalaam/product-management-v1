package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.AccountApi;
import com.kata.alten.productsmanagement.gen.model.UserRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller used for account operations
 */
@RestController
public class AccountController implements AccountApi {


    @Override
    public ResponseEntity<Void> createUserAccount(UserRegistration userRegistration) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
