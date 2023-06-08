package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandlerImp implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderMapper orderRequestMapper;

    @Override
    public void createOrder(OrderRequestDto orderRequestDto) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderRequestDto));
    }
}
