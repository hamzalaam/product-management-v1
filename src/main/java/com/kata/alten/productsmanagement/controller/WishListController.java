package com.kata.alten.productsmanagement.controller;

import com.kata.alten.productsmanagement.gen.api.ProductsApi;
import com.kata.alten.productsmanagement.gen.api.WishlistApi;
import com.kata.alten.productsmanagement.gen.model.Product;
import com.kata.alten.productsmanagement.gen.model.WishlistItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WishListController implements WishlistApi {


    @Override
    public ResponseEntity<Void> addItemToWishlist(WishlistItem wishlistItem) {
        return null;
    }

    @Override
    public ResponseEntity<List<WishlistItem>> getWishlist() {
        return null;
    }
}
