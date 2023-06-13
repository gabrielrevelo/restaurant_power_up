package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.ClientOrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidSecurityCodeException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotReadyException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.ISmsClient;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthUtil;
import com.pragma.powerup.restaurantmicroservice.domain.util.SecurityCodeGenerator;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final ISmsClient smsClient;
    private final AuthUtil authUtil;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, ISmsClient smsClient, AuthUtil authUtil) {
        this.orderPersistencePort = orderPersistencePort;
        this.smsClient = smsClient;
        this.authUtil = authUtil;
    }

    @Override
    public void createOrder(Order order) {
        Long currentClientId = authUtil.getCurrentUserId();
        String currentClientPhone = authUtil.getCurrentUserPhone();
        if (Boolean.TRUE.equals(orderPersistencePort.existsOrderInProcess(currentClientId))) {
            throw new ClientOrderInProgressException();
        }
        order.setIdClient(currentClientId);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setPhoneClient(currentClientPhone);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> listOrders(OrderStatus status, Pageable pageable) {
        Long idRestaurantOfEmployee = authUtil.getCurrentEmployeeRestaurantId();
        return orderPersistencePort.listOrders(status.name(), idRestaurantOfEmployee ,pageable);
    }

    @Override
    public void assignOrder(Long idOrder) {
        Order order = orderPersistencePort.getOrder(idOrder);
        Long idRestaurantOfEmployee = authUtil.getCurrentEmployeeRestaurantId();
        authUtil.checkEmployeeOfRestaurant(order.getIdRestaurant(), idRestaurantOfEmployee);
        Long currentEmployeeId = authUtil.getCurrentUserId();
        order.setIdChef(currentEmployeeId);
        order.setStatus(OrderStatus.IN_PREPARATION);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public void orderReady(Long idOrder) {
        Order order = orderPersistencePort.getOrder(idOrder);
        Long idRestaurantOfEmployee = authUtil.getCurrentEmployeeRestaurantId();
        authUtil.checkEmployeeOfRestaurant(order.getIdRestaurant(), idRestaurantOfEmployee);
        order.setStatus(OrderStatus.READY);
        String code = SecurityCodeGenerator.generateCode();
        order.setSecurityCode(code);
        smsClient.sendSms(order.getPhoneClient(), code, authUtil.getCurrentUserToken());
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public void orderDelivered(Long idOrder, String securityCode) {
        Order order = orderPersistencePort.getOrder(idOrder);
        Long idRestaurantOfEmployee = authUtil.getCurrentEmployeeRestaurantId();
        authUtil.checkEmployeeOfRestaurant(order.getIdRestaurant(), idRestaurantOfEmployee);
        if (order.getStatus() != OrderStatus.READY) {
            throw new OrderNotReadyException(order.getStatus().name());
        }
        if (!order.getSecurityCode().equals(securityCode)) {
            throw new InvalidSecurityCodeException();
        }
        order.setStatus(OrderStatus.DELIVERED);
        orderPersistencePort.saveOrder(order);
    }
}
