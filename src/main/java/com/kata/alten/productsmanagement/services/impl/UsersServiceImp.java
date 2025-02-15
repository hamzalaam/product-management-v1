package com.kata.alten.productsmanagement.services.impl;

import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import com.kata.alten.productsmanagement.persistence.repositories.ProductRepository;
import com.kata.alten.productsmanagement.persistence.repositories.UserRepository;
import com.kata.alten.productsmanagement.persistence.specification.ProductSpecifications;
import com.kata.alten.productsmanagement.services.ProductsService;
import com.kata.alten.productsmanagement.services.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * constructor for user service (dependency injection)
     *
     * @param userRepository user repo
     */
    public UsersServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




}
