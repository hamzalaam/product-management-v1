package com.kata.alten.productsmanagement.persistence.repositories;

import com.kata.alten.productsmanagement.persistence.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {


}