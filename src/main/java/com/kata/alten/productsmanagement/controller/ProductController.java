package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.ProductsApi;
import com.kata.alten.productsmanagement.gen.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductsApi {



    @Override
    public ResponseEntity<Void> createProduct(Product product) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts(String category, String status) {
        return null;
    }

    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateProduct(Integer id, Product product) {
        return null;
    }
}
