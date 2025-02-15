package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.exception.ExceptionEnum;
import com.kata.alten.productsmanagement.exception.CustomException;
import com.kata.alten.productsmanagement.gen.model.Product;
import com.kata.alten.productsmanagement.mapper.ProductMapper;
import com.kata.alten.productsmanagement.persistence.entities.InventoryStatus;
import com.kata.alten.productsmanagement.services.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kata.alten.productsmanagement.gen.model.Product.InventoryStatusEnum.INSTOCK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductsService productsService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private com.kata.alten.productsmanagement.persistence.entities.ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCategory("Electronics");
        product.setInventoryStatus(INSTOCK);

        productEntity = new com.kata.alten.productsmanagement.persistence.entities.ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Test Product");
        productEntity.setCategory("Electronics");
        productEntity.setInventoryStatus(InventoryStatus.INSTOCK);
    }

    @Test
    void createProduct_ShouldReturnOk() {
        // Arrange
        when(productsService.createProduct(any(com.kata.alten.productsmanagement.persistence.entities.ProductEntity.class)))
                .thenReturn(Optional.of(productEntity));

        // Act
        ResponseEntity<Void> response = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
     }

    @Test
    void deleteProduct_ShouldReturnOk() {
        // Arrange
        doNothing().when(productsService).deleteProduct(1);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productsService, times(1)).deleteProduct(1);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        when(productsService.retrieveAllProduct("INSTOCK", "Electronics"))
                .thenReturn(Optional.of(Collections.singletonList(productEntity)));

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts("Electronics", "INSTOCK");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(product.getId(), response.getBody().get(0).getId());
    }

    @Test
    void getAllProducts_ShouldReturnNotFound() {
        // Arrange
        when(productsService.retrieveAllProduct("INSTOCK", "Electronics"))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts("Electronics", "INSTOCK");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productsService, times(1)).retrieveAllProduct("INSTOCK", "Electronics");
        verify(productMapper, never()).toDtoList(anyList());
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        // Arrange
        when(productsService.retrieveProduct(1)).thenReturn(Optional.of(productEntity));


        // Act
        ResponseEntity<Product> response = productController.getProductById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(product.getId(), response.getBody().getId());
        verify(productsService, times(1)).retrieveProduct(1);
     }

    @Test
    void getProductById_ShouldThrowProductException() {
        // Arrange
        when(productsService.retrieveProduct(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            productController.getProductById(1);
        });

        assertEquals(ExceptionEnum.USER_NOT_FOUND, exception.getExceptionEnum());
        verify(productsService, times(1)).retrieveProduct(1);
        verify(productMapper, never()).toDto(any(com.kata.alten.productsmanagement.persistence.entities.ProductEntity.class));
    }

    @Test
    void updateProduct_ShouldReturnOk() {
        // Arrange
         when(productsService.updateProduct(anyInt(), any(com.kata.alten.productsmanagement.persistence.entities.ProductEntity.class)))
                .thenReturn(Optional.of(productEntity));

        // Act
        ResponseEntity<Void> response = productController.updateProduct(1, product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
     }
}