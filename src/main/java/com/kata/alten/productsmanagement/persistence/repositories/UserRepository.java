package com.kata.alten.productsmanagement.persistence.repositories;

import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // Find a user by email (unique field)
    Optional<UserEntity> findByEmail(String email);

    // Find a user by username
    Optional<UserEntity> findByUsername(String username);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Check if a user exists by username
    boolean existsByUsername(String username);
}