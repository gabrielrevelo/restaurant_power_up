package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Dish findById(Long id);
}
