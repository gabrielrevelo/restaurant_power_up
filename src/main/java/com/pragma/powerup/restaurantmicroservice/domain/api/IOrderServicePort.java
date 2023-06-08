package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;

public interface IOrderServicePort {
    void createOrder(Order order);
}
