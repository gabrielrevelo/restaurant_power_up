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

    private void checkOwnerAuthorization(Long restaurantId) {
        String userId = userServicePort.getCurrentUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(restaurantId);
        if (!restaurant.getIdOwner().equals(userId)) {
            throw new UserNotOwnerException("User not owner of restaurant");
        }
    }

    @Override
    public void saveDish(Dish dish) {
        checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Long id, Double price, String description) {
        Dish dish = dishPersistencePort.findById(id);
        checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setPrice(price);
        dish.setDescription(description);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void changeStateDish(Long id) {
        Dish dish = dishPersistencePort.findById(id);
        checkOwnerAuthorization(dish.getIdRestaurant());

        dish.setActive(!dish.getActive());
        dishPersistencePort.saveDish(dish);
    }
}
