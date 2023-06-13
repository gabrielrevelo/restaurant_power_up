package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> listOrders(OrderStatus status, Pageable pageable);

    void assignOrder(Long idOrder);

    void orderReady(Long idOrder);
}
