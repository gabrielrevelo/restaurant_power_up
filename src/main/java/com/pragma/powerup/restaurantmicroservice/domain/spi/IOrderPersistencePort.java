package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderPersistencePort {
    /**
     * Guarda una orden en la base de datos.
     *
     * @param order la orden a guardar.
     */
    void saveOrder(Order order);

    Boolean existsOrderInProcess(Long idClient);

    List<Order> listOrders(String status, Long idRestaurant, Pageable pageable);

    Order getOrder(Long idOrder);
}
