package com.kata.alten.productsmanagement.services.impl;

import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import com.kata.alten.productsmanagement.persistence.entities.UserRole;
import com.kata.alten.productsmanagement.persistence.repositories.UserRepository;
import com.kata.alten.productsmanagement.security.JwtUtils;
import com.kata.alten.productsmanagement.security.UserDetailsImpl;
import com.kata.alten.productsmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.kata.alten.productsmanagement.exception.ExceptionEnum.USER_EMAIL_EXIST;
import static com.kata.alten.productsmanagement.exception.ExceptionEnum.USER_USERNAME_EXIST;

/**
 * user service
 */
@Service
public class UsersServiceImp implements UserService {

    /**
     * user repository for CRUD ops
     */
    UserRepository userRepository;

    /**
     * password encoder based on spring security
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * authentication manager for spring security
     */
    private final AuthenticationManager authenticationManager;


    /**
     * jwt service to manage token
     */
    JwtUtils jwtUtils;

    @Value("${admin.mail}")
    private String adminMail;


    /**
     * constructor to manage dependency injections
     * @param jwtUtils jwt utils
     * @param passwordEncoder password encoder
     * @param authenticationManager authentication manager service
     * @param userRepository user repo
     */
    public UsersServiceImp(JwtUtils jwtUtils,PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * create user
     *
     * @param userEntity userEntity
     * @return created user
     */
    @Override
    public UserEntity createUser(UserEntity userEntity) {
        // Check if email or username already exists
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new CustomException(UsersServiceImp.class, USER_EMAIL_EXIST);
        }
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new CustomException(UsersServiceImp.class, USER_USERNAME_EXIST);
        }

        // Determine the role based on the email if its
        // admin mail set the property file it will get admin role
        UserRole role = adminMail.equals(userEntity.getEmail()) ? UserRole.ADMIN : UserRole.USER;

        // Create and save the user

        userEntity.setPasswordHash(passwordEncoder.encode(userEntity.getPasswordHash()));
        userEntity.setUserRole(role);
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setUpdatedAt(ZonedDateTime.now());

        return userRepository.save(userEntity);
    }

    /**
     * load user
     * @param email email from the parameter
     * @return user details
     * @throws UsernameNotFoundException error oof user not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPasswordHash()).roles(user.getUserRole().name()).build();
    }

    /**
     * login user to retrieve the token
     * @param email email
     * @param password password
     * @return retrieve token
     */
    @Override
    public String loginUser(String email, String password) {
        var clsUser = this.userRepository.findByEmail(email);
        if (clsUser.isPresent()) {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clsUser.get().getUsername(), password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwtToken = jwtUtils.generateJwtCookie(userDetails);

            return jwtToken;
        }
        throw new CustomException(UsersServiceImp.class, ExceptionEnum.USER_NOT_FOUND);
    }
}
