package com.kata.alten.productsmanagement.services;

import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * interface for user service
 */
public interface UserService {
    /**
     * user creation
     * @param userEntity user entity
     * @return user entity
     */
    UserEntity createUser(UserEntity userEntity);

    UserDetails loadUserByUsername(String username) throws CustomException;

    String loginUser(String email, String password);
}
