package com.kata.alten.productsmanagement.services;

import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * interface for product service
 */
public interface ProductsService {

    /**
     * create a new product
     * @param product product
     * @return product entity
     */
    Optional<ProductEntity> createProduct(ProductEntity product);

    /**
     * retrieve all products based on criteria ( category, status or all)
     * @param inventoryStatus optional status
     * @param category optional category
     * @return list of products
     */
    Optional<List<ProductEntity>> retrieveAllProduct(String inventoryStatus, String category);

    /**
     * retrieve product by id
     * @param id id product
     * @return returned product
     */
    Optional<ProductEntity> retrieveProduct(Integer id);

    /**
     * update product based on its id
     * @param id id of the product
     * @param product given product
     * @return product entity
     */
    Optional<ProductEntity> updateProduct(Integer id, ProductEntity product);

    /**
     * delete product
     * @param id id product
     */
    void deleteProduct(Integer id);
}
