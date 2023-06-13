package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotPendingException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Order order);

    List<Order> listOrders(OrderStatus status, Pageable pageable);

    void assignOrder(Long idOrder);

    void orderReady(Long idOrder);

    void orderDelivered(Long idOrder, String securityCode);

    /**
     * Marks an order as CANCELED.
     *
     * @param idOrder The ID of the order to cancel.
     * @throws OrderNotPendingException If the order is not in PENDING status.
     */
    void cancelOrder(Long idOrder);
}
