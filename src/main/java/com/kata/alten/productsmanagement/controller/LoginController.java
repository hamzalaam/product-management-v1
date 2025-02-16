package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.TokenApi;
import com.kata.alten.productsmanagement.gen.model.Token;
import com.kata.alten.productsmanagement.gen.model.UserCredentials;
import com.kata.alten.productsmanagement.mapper.UserMapper;
import com.kata.alten.productsmanagement.services.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
/**
 * controller used for login operations
 */
@RestController
public class LoginController implements TokenApi {

    /**
     * user service
     */
    private final UserService userService;

    /**
     * constructor for user ( dependency injection)
     * @param userService user service
     */
    public LoginController(UserService userService ) {
        this.userService = userService;
    }

    /**
     * login user
     * @param userCredentials  (required)
     * @return token string
     */

    @Override
    public ResponseEntity<Token> loginUser(UserCredentials userCredentials) {
        var tokenContent=this.userService.loginUser(userCredentials.getEmail(),userCredentials.getPassword());
        var token=new Token();
        token.setToken(tokenContent);
        return ResponseEntity.ok(token);
    }
}
