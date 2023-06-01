package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import com.pragma.powerup.restaurantmicroservice.domain.util.PaginationUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IUserClient userClient;

    private final AuthorizationUtil authorizationUtil;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IUserClient userClient, AuthorizationUtil authorizationUtil) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userClient = userClient;
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        String token = authorizationUtil.getUserToken();

        if(!Objects.equals(userClient.getUserRole(restaurant.getIdOwner(), token), "ROLE_OWNER")) {
            throw new UserNotOwnerException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> listRestaurants(int pageSize, int pageNumber) {
        List<Restaurant> restaurants = restaurantPersistencePort.listRestaurants();

        restaurants.sort(Comparator.comparing(Restaurant::getName));

        return PaginationUtil.paginate(restaurants, pageSize, pageNumber);
    }

    @Override
    public Employee registerEmployee(Long restaurantId, Employee employee) {
        authorizationUtil.checkOwnerAuthorization(restaurantId);
        String token = authorizationUtil.getUserToken();

        Long idEmployeeCreated = userClient.createEmployee(employee, token);
        employeeRestaurantPersistencePort.saveEmployeeRestaurant(idEmployeeCreated, restaurantId);

        employee.setId(idEmployeeCreated);
        employee.setIdRestaurant(restaurantId);
        return employee;
    }

}
