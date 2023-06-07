package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderPersistencePort {
    void createOrder(Order order);

    Boolean existsOrderInProcess(Long idClient);

    List<Order> listOrders(String status, Long idRestaurant, Pageable pageable);
}
