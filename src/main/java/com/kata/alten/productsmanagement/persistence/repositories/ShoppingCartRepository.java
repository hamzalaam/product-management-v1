package com.kata.alten.productsmanagement.persistence.repositories;

import com.kata.alten.productsmanagement.persistence.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {


}