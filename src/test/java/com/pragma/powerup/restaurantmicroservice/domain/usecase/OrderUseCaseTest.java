package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order.MenuSelection;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private AuthorizationUtil authorizationUtil;

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderPersistencePort, authorizationUtil);
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
        when(authorizationUtil.getCurrentUserId()).thenReturn(idClient);
        when(orderPersistencePort.existsOrderInProcess(idClient)).thenReturn(false);

        orderUseCase.createOrder(order);

        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(orderPersistencePort).createOrder(order);
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
        when(authorizationUtil.getCurrentUserId()).thenReturn(idClient);
        when(orderPersistencePort.existsOrderInProcess(idClient)).thenReturn(true);

        assertThrows(OrderInProgressException.class, () -> orderUseCase.createOrder(order));
        verify(orderPersistencePort, never()).createOrder(order);
    }

    @Test
    void listRestaurants() {
        Long restaurantId = 1L;
        OrderStatus status = OrderStatus.PENDING;
        List<Order> expectedOrders = List.of(
                new Order(),
                new Order()
        );
        when(authorizationUtil.getCurrentEmployeeRestaurantId()).thenReturn(restaurantId);
        when(orderPersistencePort.listOrders(status.name(), restaurantId, null)).thenReturn(expectedOrders);

        List<Order> result = orderUseCase.listOrders(status, null);

        assertEquals(expectedOrders, result);
        verify(authorizationUtil).getCurrentEmployeeRestaurantId();
        verify(orderPersistencePort).listOrders(status.name(), restaurantId, null);
    }
}