package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthUtil;
import org.springframework.data.domain.Pageable;
import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final AuthUtil authUtil;

    public DishUseCase(IDishPersistencePort dishPersistencePort, AuthUtil authUtil) {
        this.dishPersistencePort = dishPersistencePort;
        this.authUtil = authUtil;
    }

    @Override
    public void saveDish(Dish dish) {
        authUtil.checkOwnerOfRestaurant(dish.getIdRestaurant());

        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Long id, Double price, String description) {
        Dish dish = dishPersistencePort.findById(id);
        authUtil.checkOwnerOfRestaurant(dish.getIdRestaurant());

        dish.setPrice(price);
        dish.setDescription(description);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void changeStateDish(Long id) {
        Dish dish = dishPersistencePort.findById(id);
        authUtil.checkOwnerOfRestaurant(dish.getIdRestaurant());

        dish.setActive(!dish.getActive());
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> listDishes(Long idRestaurant, Long categoryId, Pageable pageable) {
        return dishPersistencePort.listDishes(idRestaurant, categoryId, pageable);
    }
}
