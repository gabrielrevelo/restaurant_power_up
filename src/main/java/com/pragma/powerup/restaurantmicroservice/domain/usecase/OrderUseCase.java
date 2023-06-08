package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.ClientOrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotRestaurantEmployeeException;
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
        Long currentClientId = authorizationUtil.getCurrentUserId();
        if (Boolean.TRUE.equals(orderPersistencePort.existsOrderInProcess(currentClientId))) {
            throw new ClientOrderInProgressException();
        }
        order.setIdClient(currentClientId);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> listOrders(OrderStatus status, Pageable pageable) {
        Long idRestaurantOfEmployee = authorizationUtil.getCurrentEmployeeRestaurantId();
        return orderPersistencePort.listOrders(status.name(), idRestaurantOfEmployee ,pageable);
    }

    @Override
    public void assignOrder(Long idOrder) {
        Order order = orderPersistencePort.getOrder(idOrder);
        Long idRestaurantOfEmployee = authorizationUtil.getCurrentEmployeeRestaurantId();
        if (!order.getIdRestaurant().equals(idRestaurantOfEmployee)) {
            throw new OrderNotRestaurantEmployeeException();
        }
        Long currentEmployeeId = authorizationUtil.getCurrentUserId();
        order.setIdChef(currentEmployeeId);
        order.setStatus(OrderStatus.IN_PREPARATION);
        orderPersistencePort.saveOrder(order);
    }
}
