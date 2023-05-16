package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishMapper {
    Dish toDish(DishRequestDto dishRequestDto);

    void updateDishFromDto(DishUpdateDto dishUpdateDto, @MappingTarget Dish existingDish);

}
