package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    List<RestaurantResponseDto> listRestaurants(int pageSize, int pageNumber);

    EmployeeResponseDto registerEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId);
}
