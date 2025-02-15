package com.kata.alten.productsmanagement.services.impl;

import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import com.kata.alten.productsmanagement.persistence.repositories.ProductRepository;
import com.kata.alten.productsmanagement.persistence.specification.ProductSpecifications;
import com.kata.alten.productsmanagement.services.ProductsService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * product service
 */
@Service
public class ProductsServiceImp implements ProductsService {

    /**
     * product repository for CRUD ops
     */
    ProductRepository productRepository;

    /**
     * constructor for product service (dependency injection)
     *
     * @param productRepository product repo
     */
    public ProductsServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * retrieve all products by status and category as optional
     *
     * @return list of all the products
     */
    @Override
    public Optional<List<ProductEntity>> retrieveAllProduct(String inventoryStatus, String category) {
        // Build the dynamic query using Specifications
        Specification<ProductEntity> spec = Specification
                .where(ProductSpecifications.hasInventoryStatus(inventoryStatus))
                .and(ProductSpecifications.hasCategory(category));

        // Execute the query
        return Optional.of(productRepository.findAll(spec));
    }

    @Override
    public Optional<ProductEntity> retrieveProduct(Integer id) {
        return productRepository.findById(id);
    }




    /**
     * create product
     *
     * @return product created
     */
    @Override
    public Optional<ProductEntity> createProduct(ProductEntity product) {
        try {
            var eventTime = System.currentTimeMillis();
            if (product.getId() == null) {
                product.setCreatedAt(eventTime);
            }
            product.setUpdatedAt(eventTime);

            return Optional.of(this.productRepository.save(product));
        } catch (RuntimeException e) {
            throw new CustomException(ProductsServiceImp.class, ExceptionEnum.PRODUCT_ERROR_CREATION_UPDATE);
        }
    }


    /**
     * create product
     *
     * @return product created
     */
    @Override
    public Optional<ProductEntity> updateProduct(Integer id, ProductEntity product) {
        if (this.retrieveProduct(id).isPresent()) {
            product.setId(id);
            return this.createProduct(product);
        }else {
            throw new CustomException(ProductsServiceImp.class, ExceptionEnum.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        var productToBeDeleted=this.retrieveProduct(id);
        if (productToBeDeleted.isPresent()) {
           this.productRepository.delete(productToBeDeleted.get());
        }else {
            throw new CustomException(ProductsServiceImp.class, ExceptionEnum.PRODUCT_NOT_FOUND);

        }
    }

}
