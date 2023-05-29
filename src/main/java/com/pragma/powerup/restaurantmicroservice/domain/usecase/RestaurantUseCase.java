package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestTemplateClient restTemplateClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IRestTemplateClient restTemplateClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restTemplateClient = restTemplateClient;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if(!Objects.equals(restTemplateClient.getUserRole(restaurant.getIdOwner()), "ROLE_OWNER")) {
            throw new UserNotOwnerException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> listRestaurants(int pageSize, int pageNumber) {
        List<Restaurant> restaurants = restaurantPersistencePort.listRestaurants();

        restaurants.sort(Comparator.comparing(Restaurant::getName));

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, restaurants.size());
        restaurants = restaurants.subList(startIndex, endIndex);

        return restaurants;
    }

}
