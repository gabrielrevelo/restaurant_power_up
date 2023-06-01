package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImp implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public List<RestaurantResponseDto> listRestaurants(int pageSize, int pageNumber) {
        return restaurantRequestMapper.toResponseList(restaurantServicePort.listRestaurants(pageSize, pageNumber));
    }

    @Override
    public EmployeeResponseDto registerEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId) {
        Employee employee = restaurantRequestMapper.toEmployee(employeeRequestDto);
        restaurantServicePort.registerEmployee(restaurantId, employee);
        return restaurantRequestMapper.toEmployeeResponseDto(employee);
    }
}
