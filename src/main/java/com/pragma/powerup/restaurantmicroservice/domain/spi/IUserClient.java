package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;

public interface IUserClient {

    String getUserRole(String userId, String token);

    Long createEmployee(Employee employee, String token);

}
