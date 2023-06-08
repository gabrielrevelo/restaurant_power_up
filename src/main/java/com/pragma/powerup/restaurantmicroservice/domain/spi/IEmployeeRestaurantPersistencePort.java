package com.pragma.powerup.restaurantmicroservice.domain.spi;

public interface IEmployeeRestaurantPersistencePort {
    void saveEmployeeRestaurant(Long idEmployee, Long idRestaurant);

    Long findRestaurantIdByEmployeeId(Long idEmployee);
}
