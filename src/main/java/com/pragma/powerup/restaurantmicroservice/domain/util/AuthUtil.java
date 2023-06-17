package com.pragma.powerup.restaurantmicroservice.domain.util;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotBelongClientException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotEmployeeOfRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;

public class AuthUtil {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final ICurrentUserServicePort currentUserServicePort;

    public AuthUtil(IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, ICurrentUserServicePort currentUserServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.currentUserServicePort = currentUserServicePort;
    }

    public void checkOwnerOfRestaurant(Long idRestaurant) {
        String idCurrentUser = currentUserServicePort.getCurrentUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(idRestaurant);
        if (!restaurant.getIdOwner().equals(idCurrentUser)) {
            throw new UserNotOwnerException("User not owner of restaurant");
        }
    }

    public void checkEmployeeOfRestaurant(Long idRestaurant, Long idRestaurantOfEmployee) {
        if (!idRestaurant.equals(idRestaurantOfEmployee)) {
            throw new OrderNotEmployeeOfRestaurantException();
        }
    }

    public void checkClientOfOrder(Order order) {
        Long idCurrentUser = Long.valueOf(currentUserServicePort.getCurrentUserId());
        if (!order.getIdClient().equals(idCurrentUser)) {
            throw new OrderNotBelongClientException();
        }
    }

    public String getCurrentUserToken() {
        return currentUserServicePort.getCurrentUserToken();
    }

    public Long getCurrentUserId() {
        return Long.valueOf(currentUserServicePort.getCurrentUserId());
    }

    public Long getCurrentEmployeeRestaurantId() {
        return employeeRestaurantPersistencePort.findRestaurantIdByEmployeeId(getCurrentUserId());
    }

    public String getCurrentUserPhone() {
        return currentUserServicePort.getCurrentUserPhone();
    }
}
