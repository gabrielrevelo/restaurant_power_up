package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishUpdateDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);

    void updateDish(Long id, DishUpdateDto dishUpdateDto);
}
