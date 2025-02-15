package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.CartApi;
import com.kata.alten.productsmanagement.gen.api.WishlistApi;
import com.kata.alten.productsmanagement.gen.model.CartItem;
import com.kata.alten.productsmanagement.gen.model.WishlistItem;
import com.kata.alten.productsmanagement.utils.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * controller used for cart operations
 */
@RestController
public class CartController implements CartApi {


    @Override
    public ResponseEntity<Void> addItemToCart(CartItem cartItem) {
        // Return 501 Not Implemented with the error response
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<List<CartItem>> getShoppingCart() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
