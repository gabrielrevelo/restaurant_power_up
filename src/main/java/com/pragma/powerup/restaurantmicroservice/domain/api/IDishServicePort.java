package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Double price, String description);

    void changeStateDish(Long id);

    List<Dish> listDishes(Long idRestaurant, Long categoryId, Pageable pageable);
}
