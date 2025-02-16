package com.kata.alten.productsmanagement.services.impl;

import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import com.kata.alten.productsmanagement.persistence.entities.UserRole;
import com.kata.alten.productsmanagement.persistence.repositories.UserRepository;
import com.kata.alten.productsmanagement.security.JwtUtils;
import com.kata.alten.productsmanagement.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UsersServiceImp usersService;

    private UserEntity testUser;
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword123";
    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String JWT_TOKEN = "jwt.token.string";

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername(TEST_USERNAME);
        testUser.setPasswordHash(TEST_PASSWORD);

        // Set admin email using reflection (simulating @Value injection)
        ReflectionTestUtils.setField(usersService, "adminMail", ADMIN_EMAIL);
    }

    @Nested
    @DisplayName("CreateUser Tests")
    class CreateUserTests {

        @Test
        @DisplayName("Should successfully create a regular user")
        void shouldCreateRegularUser() {
            // Arrange
            when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(false);
            when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
            when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(ENCODED_PASSWORD);
            when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

            // Act
            UserEntity createdUser = usersService.createUser(testUser);

            // Assert
            assertNotNull(createdUser);
            assertEquals(UserRole.USER, createdUser.getUserRole());
            verify(passwordEncoder).encode(TEST_PASSWORD);
            verify(userRepository).save(any(UserEntity.class));
        }

        @Test
        @DisplayName("Should create admin user when email matches admin email")
        void shouldCreateAdminUser() {
            // Arrange
            testUser.setEmail(ADMIN_EMAIL);
            when(userRepository.existsByEmail(ADMIN_EMAIL)).thenReturn(false);
            when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
            when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(ENCODED_PASSWORD);
            when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

            // Act
            UserEntity createdUser = usersService.createUser(testUser);

            // Assert
            assertNotNull(createdUser);
            assertEquals(UserRole.ADMIN, createdUser.getUserRole());
        }

        @Test
        @DisplayName("Should throw exception when email already exists")
        void shouldThrowExceptionWhenEmailExists() {
            // Arrange
            when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(true);

            // Act & Assert
            assertThrows(CustomException.class, () -> usersService.createUser(testUser));
            verify(userRepository, never()).save(any(UserEntity.class));
        }

        @Test
        @DisplayName("Should throw exception when username already exists")
        void shouldThrowExceptionWhenUsernameExists() {
            // Arrange
            when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(false);
            when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);

            // Act & Assert
            assertThrows(CustomException.class, () -> usersService.createUser(testUser));
            verify(userRepository, never()).save(any(UserEntity.class));
        }
    }

    @Nested
    @DisplayName("LoadUserByUsername Tests")
    class LoadUserByUsernameTests {

        @Test
        @DisplayName("Should successfully load user by username")
        void shouldLoadUserByUsername() {
            // Arrange
            testUser.setPasswordHash(ENCODED_PASSWORD);
            testUser.setUserRole(UserRole.USER);
            when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

            // Act
            var userDetails = usersService.loadUserByUsername(TEST_EMAIL);

            // Assert
            assertNotNull(userDetails);
            assertEquals(TEST_USERNAME, userDetails.getUsername());
            assertEquals(ENCODED_PASSWORD, userDetails.getPassword());
            assertTrue(userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(UsernameNotFoundException.class,
                    () -> usersService.loadUserByUsername(TEST_EMAIL));
        }
    }

    @Nested
    @DisplayName("LoginUser Tests")
    class LoginUserTests {

        @Test
        @DisplayName("Should successfully login user and return JWT token")
        void shouldLoginUserSuccessfully() {
            // Arrange
            testUser.setPasswordHash(ENCODED_PASSWORD);
            Authentication authentication = mock(Authentication.class);
            UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

            when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(jwtUtils.generateJwtCookie(userDetails)).thenReturn(JWT_TOKEN);

            // Act
            String token = usersService.loginUser(TEST_EMAIL, TEST_PASSWORD);

            // Assert
            assertNotNull(token);
            assertEquals(JWT_TOKEN, token);
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(jwtUtils).generateJwtCookie(userDetails);
        }

        @Test
        @DisplayName("Should throw exception when user not found during login")
        void shouldThrowExceptionWhenUserNotFoundDuringLogin() {
            // Arrange
            when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CustomException.class,
                    () -> usersService.loginUser(TEST_EMAIL, TEST_PASSWORD));
            verify(authenticationManager, never())
                    .authenticate(any(UsernamePasswordAuthenticationToken.class));
        }

        @Test
        @DisplayName("Should throw exception when authentication fails")
        void shouldThrowExceptionWhenAuthenticationFails() {
            // Arrange
            when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new RuntimeException("Authentication failed"));

            // Act & Assert
            assertThrows(RuntimeException.class,
                    () -> usersService.loginUser(TEST_EMAIL, TEST_PASSWORD));
        }
    }
}