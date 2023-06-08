package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto);
}
