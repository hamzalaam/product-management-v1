package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.TokenApi;
import com.kata.alten.productsmanagement.gen.model.Token;
import com.kata.alten.productsmanagement.gen.model.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
/**
 * controller used for login operations
 */
@RestController
public class LoginController implements TokenApi {


    @Override
    public ResponseEntity<Token> loginUser(UserCredentials userCredentials) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
