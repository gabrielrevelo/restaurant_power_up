package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishMapper {
    @Mapping(target = "category", expression = "java(mapCategory(dishRequestDto.getCategory()))")
    Dish toDish(DishRequestDto dishRequestDto);

    default Category mapCategory(Long idCategory) {
        return new Category(idCategory, null, null);
    }

    @Mapping(target = "category", source = "category.id")
    DishResponseDto toResponseDto(Dish dish);

    List<DishResponseDto> toResponseList(List<Dish> dishList);
}
