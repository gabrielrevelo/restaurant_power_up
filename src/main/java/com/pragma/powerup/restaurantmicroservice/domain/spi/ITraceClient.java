package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;

public interface ITraceClient {
    void trace(Order order, String oldStatus, String token);
}
