package com.kata.alten.productsmanagement.services;

import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * interface for product service
 */
public interface ProductsService {

    Optional<ProductEntity> createProduct(ProductEntity product);

    Optional<List<ProductEntity>> retrieveAllProduct(String inventoryStatus, String category);

    Optional<ProductEntity> retrieveProduct(Integer id);

    Optional<ProductEntity> updateProduct(Integer id, ProductEntity product);

    void deleteProduct(Integer id);
}
