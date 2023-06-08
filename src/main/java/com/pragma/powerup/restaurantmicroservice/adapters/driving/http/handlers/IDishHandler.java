package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);

    void updateDish(Long id, DishUpdateDto dishUpdateDto);

    void changeStateDish(Long id);

    List<DishResponseDto> listDishes(Long idRestaurant, Long categoryId, Pageable pageable);
}
