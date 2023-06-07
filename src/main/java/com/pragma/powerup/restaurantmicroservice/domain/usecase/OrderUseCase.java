package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final AuthorizationUtil authorizationUtil;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, AuthorizationUtil authorizationUtil) {
        this.orderPersistencePort = orderPersistencePort;
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    public void createOrder(Order order) {
        Long currentUserId = authorizationUtil.getCurrentUserId();
        if (Boolean.TRUE.equals(orderPersistencePort.existsOrderInProcess(currentUserId))) {
            throw new OrderInProgressException();
        }
        order.setIdClient(currentUserId);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        orderPersistencePort.createOrder(order);
    }

    @Override
    public List<Order> listOrders(OrderStatus status, Pageable pageable) {
        Long idRestaurant = authorizationUtil.getCurrentEmployeeRestaurantId();
        return orderPersistencePort.listOrders(status.name(), idRestaurant ,pageable);
    }
}
