package com.kata.alten.productsmanagement.persistence.repositories;

import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * repository for products to manage crud ops
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>,
        JpaSpecificationExecutor<ProductEntity> {


}