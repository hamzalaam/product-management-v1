package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.model.UserRegistration;
import com.kata.alten.productsmanagement.mapper.UserMapper;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import com.kata.alten.productsmanagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    private UserMapper userMapper;

    private UserRegistration userRegistration;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        // Initialize test data
        userRegistration = new UserRegistration();
        userRegistration.setEmail("test@example.com");
        userRegistration.setUsername("testuser");
        userRegistration.setPassword("password");

        userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setUsername("testuser");
        userEntity.setPasswordHash("encodedPassword");
    }

    @Test
    void createUserAccount_Success() {
        // Arrange
        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        ResponseEntity<Void> response = accountController.createUserAccount(userRegistration);

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);
        verify(userService).createUser(any(UserEntity.class));
    }

    @Test
    void createUserAccount_VerifyUserMapper() {
        // Arrange
        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        ResponseEntity<Void> response = accountController.createUserAccount(userRegistration);

        // Assert
        verify(userService).createUser(argThat(user ->
                user.getEmail().equals("test@example.com") &&
                        user.getUsername().equals("testuser") &&
                        user.getPasswordHash() != null // Password is encoded, so we can't predict the exact value
        ));
    }
}