package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.gen.api.ProductsApi;
import com.kata.alten.productsmanagement.gen.model.Product;
import com.kata.alten.productsmanagement.mapper.ProductMapper;
import com.kata.alten.productsmanagement.services.ProductsService;
import com.kata.alten.productsmanagement.services.impl.ProductsServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller exposing end point used for products
 */
@RestController
public class ProductController implements ProductsApi {

    /**
     * product service to manage products
     */
    private final ProductsService productsService;

    /**
     * product service to manage products
     */
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * constructor the controller (dependency injection)
     *
     * @param productsService product service
     */
    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    /**
     * create product
     *
     * @param product (required)
     * @return created product
     */
    @Override
    public ResponseEntity<Void> createProduct(Product product) {

        var productEntity = productMapper.toEntity(product);
        this.productsService.createProduct(productEntity);
        return ResponseEntity.ok().build();
    }

    /**
     * delete product by it's id
     * @param id Product ID (required)
     * @return void
     */
    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        this.productsService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    /**
     * retrieve list of product
     *
     * @param category        Filter by category (optional)
     * @param inventoryStatus Filter by inventory status (optional)
     * @return list of products
     */
    @Override
    public ResponseEntity<List<Product>> getAllProducts(String category, String inventoryStatus) {

        var productList = this.productsService.retrieveAllProduct(inventoryStatus, category).map(productMapper::toDtoList);
        return productList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * retrieve product by its id
     *
     * @param id Product ID (required)
     * @return product
     */

    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        var product = this.productsService.retrieveProduct(id).map(productMapper::toDto);
        return product.map(ResponseEntity::ok).orElseThrow(() -> new CustomException(ProductsServiceImp.class, ExceptionEnum.USER_NOT_FOUND));
    }

    /**
     * update product by its id
     * @param id Product ID (required)
     * @param product  (required)
     * @return void
     */
    @Override
    public ResponseEntity<Void> updateProduct(Integer id, Product product) {
        var productEntity = productMapper.toEntity(product);
        this.productsService.updateProduct(id,productEntity);
        return ResponseEntity.ok().build();
    }
}
