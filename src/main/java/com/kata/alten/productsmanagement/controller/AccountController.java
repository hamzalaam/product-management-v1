package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.AccountApi;
import com.kata.alten.productsmanagement.gen.model.UserRegistration;
import com.kata.alten.productsmanagement.mapper.UserMapper;
import com.kata.alten.productsmanagement.services.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller used for account operations
 */
@RestController
public class AccountController implements AccountApi {

    /**
     * user service
     */
    private final UserService userService;
    /**
     * user mapper
     */
    private final UserMapper userMapper  = Mappers.getMapper(UserMapper.class);

    /**
     * constructor for user ( dependency injection)
     * @param userService user service
     */
    public AccountController(UserService userService ) {
        this.userService = userService;
     }

    /***
     * end point to create user
     * @param userRegistration  (required)
     * @return void
     */
    @Override
    public ResponseEntity<Void> createUserAccount(UserRegistration userRegistration) {
        var entity = userMapper.toEntity(userRegistration);
        this.userService.createUser(entity);
        return ResponseEntity.ok().build();
    }
}
