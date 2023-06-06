package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final AuthorizationUtil authorizationUtil;

    public DishUseCase(IDishPersistencePort dishPersistencePort, AuthorizationUtil authorizationUtil) {
        this.dishPersistencePort = dishPersistencePort;
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    public void saveDish(Dish dish) {
        authorizationUtil.checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Long id, Double price, String description) {
        Dish dish = dishPersistencePort.findById(id);
        authorizationUtil.checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setPrice(price);
        dish.setDescription(description);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void changeStateDish(Long id) {
        Dish dish = dishPersistencePort.findById(id);
        authorizationUtil.checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setActive(!dish.getActive());
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> listDishes(Long idRestaurant, Long categoryId, Pageable pageable) {
        return dishPersistencePort.listDishes(idRestaurant, categoryId, pageable);
    }
}
