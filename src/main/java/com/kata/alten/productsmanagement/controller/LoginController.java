package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.TokenApi;
import com.kata.alten.productsmanagement.gen.model.Token;
import com.kata.alten.productsmanagement.gen.model.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements TokenApi {


    @Override
    public ResponseEntity<Token> loginUser(UserCredentials userCredentials) {
        return null;
    }
}
