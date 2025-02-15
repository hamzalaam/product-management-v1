package com.kata.alten.productsmanagement.service;

import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import com.kata.alten.productsmanagement.persistence.repositories.ProductRepository;
import com.kata.alten.productsmanagement.services.impl.ProductsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kata.alten.productsmanagement.persistence.entities.InventoryStatus.INSTOCK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductsServiceImp productsService;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        product = new ProductEntity();
        product.setId(1);
        product.setName("Test Product");
        product.setCategory("Electronics");
        product.setInventoryStatus(INSTOCK);
    }

    @Test
    void retrieveAllProduct_ShouldReturnListOfProducts() {
        // Arrange
        when(productRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.singletonList(product));

        // Act
        Optional<List<ProductEntity>> result = productsService.retrieveAllProduct("INSTOCK", "Electronics");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(product, result.get().get(0));
        verify(productRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void retrieveProduct_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        Optional<ProductEntity> result = productsService.retrieveProduct(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void retrieveProduct_ShouldReturnEmptyOptional() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<ProductEntity> result = productsService.retrieveProduct(1);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Arrange
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        // Act
        Optional<ProductEntity> result = productsService.createProduct(product);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void createProduct_ShouldThrowProductException() {
        // Arrange
        when(productRepository.save(any(ProductEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            productsService.createProduct(product);
        });

        assertEquals(ExceptionEnum.PRODUCT_ERROR_CREATION_UPDATE, exception.getExceptionEnum());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        // Act
        Optional<ProductEntity> result = productsService.updateProduct(1, product);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void updateProduct_ShouldThrowProductException() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            productsService.updateProduct(1, product);
        });

        assertEquals(ExceptionEnum.PRODUCT_NOT_FOUND, exception.getExceptionEnum());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(ProductEntity.class));

        // Act
        productsService.deleteProduct(1);

        // Assert
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).delete(any(ProductEntity.class));
    }

    @Test
    void deleteProduct_ShouldThrowProductException() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            productsService.deleteProduct(1);
        });

        assertEquals(ExceptionEnum.PRODUCT_NOT_FOUND, exception.getExceptionEnum());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, never()).delete(any(ProductEntity.class));
    }
}