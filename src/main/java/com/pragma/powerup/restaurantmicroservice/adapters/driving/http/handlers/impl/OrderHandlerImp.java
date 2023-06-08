package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandlerImp implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderMapper orderRequestMapper;

    @Override
    public void createOrder(OrderRequestDto orderRequestDto) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderRequestDto));
    }

    @Override
    public List<OrderResponseDto> listOrders(OrderStatus status, Pageable pageable) {
        return orderRequestMapper.toOrderResponseDtoList(orderServicePort.listOrders(status, pageable));
    }
}
