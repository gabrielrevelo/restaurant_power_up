package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.ClientOrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order.MenuSelection;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthUtil;
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

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderPersistencePort, null, authUtil);
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
        Long idRestaurant = 1L;
        Long idEmployee = 1L;
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurant);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurant);
        when(authUtil.getCurrentUserId()).thenReturn(idEmployee);

        orderUseCase.assignOrder(idOrder);

        assertEquals(idEmployee, order.getIdChef());
        assertEquals(OrderStatus.IN_PREPARATION, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void orderReady() {
        Long idOrder = 1L;
        Long idRestaurant = 1L;
        Order order = new Order();
        order.setId(idOrder);
        order.setIdRestaurant(idRestaurant);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(authUtil.getCurrentEmployeeRestaurantId()).thenReturn(idRestaurant);

        orderUseCase.orderReady(idOrder);

        assertEquals(OrderStatus.READY, order.getStatus());
        verify(orderPersistencePort).saveOrder(order);
    }
}