package com.kata.alten.productsmanagement.mapper;
import com.kata.alten.productsmanagement.gen.model.Product;
import com.kata.alten.productsmanagement.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * product mapper
 */
@Mapper
public interface ProductMapper {
    /**
     * instance of mapper
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * to map entity to dto
     * @param product product entity
     * @return product dto
     */
    Product toDto(ProductEntity product);
    /**
     * to map entity list to dto list
     * @param product product entity list
     * @return product dto list
     */
    List<Product> toDtoList(List<ProductEntity> product);
    /**
     * to map dto to entiry
     * @param product product dto
     * @return product entity
     */
    ProductEntity toEntity(Product product);


}
