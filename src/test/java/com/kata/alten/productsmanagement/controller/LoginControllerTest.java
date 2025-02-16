package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.model.Token;
import com.kata.alten.productsmanagement.gen.model.UserCredentials;
import com.kata.alten.productsmanagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    private UserCredentials userCredentials;
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_TOKEN = "jwt.token.string";

    @BeforeEach
    void setUp() {
        userCredentials = new UserCredentials();
        userCredentials.setEmail(TEST_EMAIL);
        userCredentials.setPassword(TEST_PASSWORD);
    }

    @Nested
    @DisplayName("LoginUser Tests")
    class LoginUserTests {

        @Test
        @DisplayName("Should successfully login and return token")
        void shouldLoginSuccessfully() {
            // Arrange
            when(userService.loginUser(TEST_EMAIL, TEST_PASSWORD)).thenReturn(TEST_TOKEN);

            // Act
            ResponseEntity<Token> response = loginController.loginUser(userCredentials);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(TEST_TOKEN, response.getBody().getToken());
            verify(userService).loginUser(TEST_EMAIL, TEST_PASSWORD);
        }

        @Test
        @DisplayName("Should handle null credentials")
        void shouldHandleNullCredentials() {
            // Arrange
            userCredentials.setEmail(null);
            userCredentials.setPassword(null);
            when(userService.loginUser(null, null)).thenThrow(new IllegalArgumentException("Invalid credentials"));

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> loginController.loginUser(userCredentials));
            verify(userService).loginUser(null, null);
        }

        @Test
        @DisplayName("Should propagate service exceptions")
        void shouldPropagateServiceExceptions() {
            // Arrange
            when(userService.loginUser(anyString(), anyString()))
                    .thenThrow(new RuntimeException("Service error"));

            // Act & Assert
            assertThrows(RuntimeException.class,
                    () -> loginController.loginUser(userCredentials));
            verify(userService).loginUser(TEST_EMAIL, TEST_PASSWORD);
        }

        @Test
        @DisplayName("Should create token with empty string when service returns null")
        void shouldHandleNullTokenFromService() {
            // Arrange
            when(userService.loginUser(TEST_EMAIL, TEST_PASSWORD)).thenReturn(null);

            // Act
            ResponseEntity<Token> response = loginController.loginUser(userCredentials);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNull(response.getBody().getToken());
            verify(userService).loginUser(TEST_EMAIL, TEST_PASSWORD);
        }

        @Test
        @DisplayName("Should handle empty credentials")
        void shouldHandleEmptyCredentials() {
            // Arrange
            userCredentials.setEmail("");
            userCredentials.setPassword("");
            when(userService.loginUser("", "")).thenThrow(new IllegalArgumentException("Empty credentials"));

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> loginController.loginUser(userCredentials));
            verify(userService).loginUser("", "");
        }
    }
}