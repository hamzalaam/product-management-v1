package com.kata.alten.productsmanagement.persistence.repositories;

 import com.kata.alten.productsmanagement.persistence.entities.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Integer> {


}