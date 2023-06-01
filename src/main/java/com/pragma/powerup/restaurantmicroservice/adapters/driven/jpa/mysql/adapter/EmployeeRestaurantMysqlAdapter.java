package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRestaurantRepository;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeRestaurantMysqlAdapter implements IEmployeeRestaurantPersistencePort {

    private final IEmployeeRestaurantRepository employeeRestaurantRepository;

    @Override
    public void saveEmployeeRestaurant(Long idEmployee, Long idRestaurant) {
        EmployeeRestaurantEntity employeeRestaurantEntity = new EmployeeRestaurantEntity();
        EmployeeRestaurantEntity.EmployeeRestaurantId employeeRestaurantId = new EmployeeRestaurantEntity.EmployeeRestaurantId();
        employeeRestaurantId.setIdEmployee(idEmployee);
        employeeRestaurantId.setIdRestaurant(idRestaurant);
        employeeRestaurantEntity.setId(employeeRestaurantId);
        employeeRestaurantRepository.save(employeeRestaurantEntity);
    }
}