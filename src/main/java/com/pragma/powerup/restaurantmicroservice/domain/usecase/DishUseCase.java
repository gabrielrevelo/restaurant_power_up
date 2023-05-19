package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICurrentUserServicePort userServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ICurrentUserServicePort userServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveDish(Dish dish) {
        String idUser = userServicePort.getCurrentUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(dish.getIdRestaurant());
        if(!restaurant.getIdOwner().equals(idUser)) {
            throw new UserNotOwnerException("User not owner of restaurant");
        }
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish findById(Long id) {
        return dishPersistencePort.findById(id);
    }
}
