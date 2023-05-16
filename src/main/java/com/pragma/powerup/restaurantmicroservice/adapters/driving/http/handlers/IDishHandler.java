package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
}
