package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Dish findById(Long id);

    List<Dish> listDishes(Long idRestaurant, Long categoryId, Pageable pageable);
}
