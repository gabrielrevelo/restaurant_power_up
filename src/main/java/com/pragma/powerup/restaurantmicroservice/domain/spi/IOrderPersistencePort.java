package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;

public interface IOrderPersistencePort {
    void createOrder(Order order);

    Boolean existsOrderInProcess(Long idClient);
}
