package com.kata.alten.productsmanagement.persistence.specification;

import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * product specification to allow to query 4 scenarios based on the input
 * Case 1 : no category or status filled : return all products
 * Case 2 : only category is filled: return all product within the category
 * Case 3 : only status is filled: return all product within given status
 * Case 4 : both category and status filled: return all products within both criteria
 */
@NoArgsConstructor
public class ProductSpecifications {


    /**
     * specification has inventory status
     * @param inventoryStatus status
     * @return spec prod entity
     */
    public static Specification<ProductEntity> hasInventoryStatus(String inventoryStatus) {
        return (root, query, criteriaBuilder) ->
                inventoryStatus == null ? null : criteriaBuilder.equal(root.get("inventoryStatus"), inventoryStatus);
    }
    /**
     * specification has category
     * @param category category
     * @return spec prod entity
     */
    public static Specification<ProductEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                    category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }
}
