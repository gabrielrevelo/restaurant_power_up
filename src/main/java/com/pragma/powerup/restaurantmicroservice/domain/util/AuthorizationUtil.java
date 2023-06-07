package com.pragma.powerup.restaurantmicroservice.domain.util;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;

public class AuthorizationUtil {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final ICurrentUserServicePort currentUserServicePort;

    public AuthorizationUtil(IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, ICurrentUserServicePort currentUserServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.currentUserServicePort = currentUserServicePort;
    }

    public void checkOwnerAuthorization(Long restaurantId) {
        String currentUserId = currentUserServicePort.getCurrentUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(restaurantId);
        if (!restaurant.getIdOwner().equals(currentUserId)) {
            throw new UserNotOwnerException("User not owner of restaurant");
        }
    }

    public String getUserToken() {
        return currentUserServicePort.getCurrentUserToken();
    }

    public Long getCurrentUserId() {
        return Long.valueOf(currentUserServicePort.getCurrentUserId());
    }

    public Long getCurrentEmployeeRestaurantId() {
        return employeeRestaurantPersistencePort.findRestaurantIdByEmployeeId(getCurrentUserId());
    }
}
