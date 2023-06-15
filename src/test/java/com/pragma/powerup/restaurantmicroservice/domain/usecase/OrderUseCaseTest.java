package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.ClientOrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidSecurityCodeException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotPendingException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotReadyException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order.MenuSelection;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.ISmsClient;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthUtil;
import com.pragma.powerup.restaurantmicroservice.domain.util.SecurityCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private ISmsClient smsClient;

    @Mock
    private SecurityCodeGenerator securityCodeGenerator;

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderPersistencePort, smsClient, authUtil, securityCodeGenerator);
    }

    @Test
    void createOrder_noExistOrderInProcess() {
        Long idRestaurant = 1L;
        List<MenuSelection> menuSelections = List.of(
                new MenuSelection(1L, 2),
                new MenuSelection(2L, 2));
        Order order = new Order();
        order.setIdRestaurant(idRestaurant);
        order.setMenuSelections(menuSelections);
        Long idClient = 1L;
        when(authUtil.getCurrentUserId()).thenReturn(idClient);
        when(orderPersistencePort.existsOrderInProcess(idClient)).thenReturn(false);

        orderUseCase.createOrder(order);

        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void createOrder_ExistOrderInProcess() {
        Long idRestaurant = 1L;
        List<MenuSelection> menuSelections = List.of(
                new MenuSelection(1L, 2),
                new MenuSelection(2L, 2));
        Order order = new Order();
        order.setIdRestaurant(idRestaurant);
        order.setMenuSelections(menuSelections);
        Long idClient = 1L;
        when(authUtil.getCurrentUserId()).thenReturn(idClient);
        when(orderPersistencePort.existsOrderInProcess(idClient)).thenReturn(true);

        assertThrows(ClientOrderInProgressException.class, () -> orderUseCase.createOrder(order));
        verify(orderPersistencePort, never()).saveOrder(order);
    }

    @Test
    void listRestaurants() {
        Long restaurantId = 1L;
        OrderStatus status = OrderStatus.PENDING;
        List<Order> expectedOrders = List.of(
                new Order(),
                new Order()
        );
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(restaurantId);
        when(orderPersistencePort.listOrders(status.name(), restaurantId, null)).thenReturn(expectedOrders);

        List<Order> result = orderUseCase.listOrders(status, null);

        assertEquals(expectedOrders, result);
        verify(authUtil).getCurrentEmployeeRestaurantId();
        verify(orderPersistencePort).listOrders(status.name(), restaurantId, null);
    }

    @Test
    void assignOrder() {
        Long idOrder = 1L;
        Long idRestaurantOfEmployee = 1L;
        Long idEmployee = 1L;
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurantOfEmployee);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurantOfEmployee);
        when(authUtil.getCurrentUserId()).thenReturn(idEmployee);

        orderUseCase.assignOrder(idOrder);

        assertEquals(idEmployee, order.getIdChef());
        assertEquals(OrderStatus.IN_PREPARATION, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void orderReady() {
        Long idOrder = 1L;
        Long idRestaurantOfEmployee = 1L;
        String securityCode = "1234";
        String token = "token";
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurantOfEmployee);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurantOfEmployee);
        doNothing().when(authUtil).checkEmployeeOfRestaurant(anyLong(), anyLong());
        when(securityCodeGenerator.generateCode()).thenReturn(securityCode);
        when(authUtil.getCurrentUserToken()).thenReturn(token);

        orderUseCase.orderReady(idOrder);

        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(securityCode, order.getSecurityCode());
        verify(orderPersistencePort).saveOrder(order);
        verify(smsClient).sendSms(order.getPhoneClient(), securityCode, token);
    }

    @Test
    void deliverOrder() {
        Long idOrder = 1L;
        Long idRestaurantOfEmployee = 1L;
        String securityCode = "1234";
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurantOfEmployee);
        order.setSecurityCode(securityCode);
        order.setStatus(OrderStatus.READY);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurantOfEmployee);
        doNothing().when(authUtil).checkEmployeeOfRestaurant(anyLong(), anyLong());

        orderUseCase.deliverOrder(idOrder, securityCode);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void deliverOrder_OrderNotReady() {
        Long idOrder = 1L;
        Long idRestaurantOfEmployee = 1L;
        String securityCode = "1234";
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurantOfEmployee);
        order.setSecurityCode(securityCode);
        order.setStatus(OrderStatus.PENDING);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurantOfEmployee);
        doNothing().when(authUtil).checkEmployeeOfRestaurant(anyLong(), anyLong());

        assertThrows(OrderNotReadyException.class, () -> orderUseCase.deliverOrder(idOrder, securityCode));
        verify(orderPersistencePort, never()).saveOrder(order);
    }

    @Test
    void deliverOrder_InvalidSecurityCode() {
        Long idOrder = 1L;
        Long idRestaurantOfEmployee = 1L;
        String securityCode = "1234";
        String otherSecurityCode = "4321";
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurantOfEmployee);
        order.setSecurityCode(securityCode);
        order.setStatus(OrderStatus.READY);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurantOfEmployee);
        doNothing().when(authUtil).checkEmployeeOfRestaurant(anyLong(), anyLong());

        assertThrows(InvalidSecurityCodeException.class, () -> orderUseCase.deliverOrder(idOrder, otherSecurityCode));
        verify(orderPersistencePort, never()).saveOrder(order);
    }

    @Test
    void cancelOrder() {
        Long idOrder = 1L;
        Long idClient = 1L;
        Order order = new Order();
        order.setId(idOrder);
        order.setIdClient(idClient);
        order.setStatus(OrderStatus.PENDING);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        doNothing().when(authUtil).checkClientOfOrder(order);

        orderUseCase.cancelOrder(idOrder);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void cancelOrder_OrderNotPending() {
        Long idOrder = 1L;
        Long idClient = 1L;
        Order order = new Order();
        order.setId(idOrder);
        order.setIdClient(idClient);
        order.setStatus(OrderStatus.IN_PREPARATION);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        doNothing().when(authUtil).checkClientOfOrder(order);

        assertThrows(OrderNotPendingException.class, () -> orderUseCase.cancelOrder(idOrder));
        verify(orderPersistencePort, never()).saveOrder(order);
    }
}