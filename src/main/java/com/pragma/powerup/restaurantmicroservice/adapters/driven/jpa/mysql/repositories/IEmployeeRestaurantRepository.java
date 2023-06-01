package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity.EmployeeRestaurantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRestaurantRepository extends JpaRepository<EmployeeRestaurantEntity, EmployeeRestaurantId> {
}
