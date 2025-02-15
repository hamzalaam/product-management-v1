package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.CartApi;
import com.kata.alten.productsmanagement.gen.api.WishlistApi;
import com.kata.alten.productsmanagement.gen.model.CartItem;
import com.kata.alten.productsmanagement.gen.model.WishlistItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController implements CartApi {


    @Override
    public ResponseEntity<Void> addItemToCart(CartItem cartItem) {
        return null;
    }

    @Override
    public ResponseEntity<List<CartItem>> getShoppingCart() {
        return null;
    }
}
