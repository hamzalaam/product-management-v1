package com.kata.alten.productsmanagement.mapper;

import com.kata.alten.productsmanagement.gen.model.UserRegistration;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * user mapper
 */
@Mapper
public interface UserMapper {

    /**
     * to map entity to dto
     *
     * @param entity user entity
     * @return user dto
     */
    UserRegistration toDto(UserEntity entity);


    /**
     * to map dto to entity
     *
     * @param userInput user dto
     * @return user entity
     */
    @Mapping(source = "password", target = "passwordHash")
    UserEntity toEntity(UserRegistration userInput);


}
